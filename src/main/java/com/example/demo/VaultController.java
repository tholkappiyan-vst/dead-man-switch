package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class VaultController {

    @Autowired
    private SecretRepository secretRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/access")
    public String accessVault(@RequestParam String userEmail, Model model) {
        List<SecretVault> userSecrets = secretRepository.findByUserEmail(userEmail);
        model.addAttribute("secrets", userSecrets);
        model.addAttribute("userEmail", userEmail);
        return "dashboard";
    }

    @PostMapping("/create")
    public String createSecret(@RequestParam String userEmail,
                               @RequestParam String title,
                               @RequestParam String secretData,
                               @RequestParam String beneficiaryEmail,
                               @RequestParam int intervalMinutes,
                               Model model) {
        SecretVault secret = new SecretVault();
        secret.setUserEmail(userEmail);
        secret.setTitle(title);
        secret.setSecretData(secretData);
        secret.setBeneficiaryEmail(beneficiaryEmail);
        secret.setIntervalMinutes(intervalMinutes);
        secret.setLastCheckIn(LocalDateTime.now());
        secret.setStatus("ACTIVE");

        secretRepository.save(secret);
        return "redirect:/?email=" + userEmail; 
    }

    @GetMapping("/reset")
    public String resetVault(@RequestParam Long id) {
        SecretVault secret = secretRepository.findById(id).orElse(null);
        if (secret != null && "PENDING_PROOF".equals(secret.getStatus())) {
            secret.setStatus("ACTIVE");
            secret.setLastCheckIn(LocalDateTime.now());
            secretRepository.save(secret);
        }
        return "redirect:/";
    }
}