package com.example.demo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VaultScheduler {

    @Autowired
    private SecretRepository secretRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.base.url}")
    private String baseUrl;

    @Scheduled(fixedRate = 10000)
    public void checkDeadMansSwitch() {
        List<SecretVault> secrets = secretRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (SecretVault secret : secrets) {
            if (secret.getLastCheckIn() == null) {
                secret.setLastCheckIn(now);
                secretRepository.save(secret);
                continue;
            }

            long minutesElapsed = ChronoUnit.MINUTES.between(secret.getLastCheckIn(), now);

            // PHASE 1: Time has passed interval limits -> Trigger Ping to Owner
            if (minutesElapsed >= secret.getIntervalMinutes() && "ACTIVE".equals(secret.getStatus())) {
                try {
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(secret.getUserEmail());
                    message.setSubject("DMS Alert: Proof of Life Required");
                    message.setText("Are you okay? Click here to reset your vault safety lock: " +
                                    baseUrl + "/reset?id=" + secret.getId());
                    mailSender.send(message);

                    secret.setStatus("PENDING_PROOF");
                    secretRepository.save(secret);
                    System.out.println(">>> Proof-of-life email successfully dispatched to owner: " + secret.getUserEmail());
                } catch (Exception e) {
                    System.out.println(">>> Error sending mail to owner: " + e.getMessage());
                }
            }
            
            // PHASE 2: Grace period expired -> Trigger Transfer to Beneficiary
            else if (minutesElapsed >= (secret.getIntervalMinutes() * 2) && "PENDING_PROOF".equals(secret.getStatus())) {
                try {
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(secret.getBeneficiaryEmail());
                    message.setSubject("DMS Final Notice: Secure Asset Payload Release");
                    message.setText("The dead-man's switch triggered. Secure Data: " + secret.getSecretData());
                    mailSender.send(message);

                    secret.setStatus("RELEASED");
                    secretRepository.save(secret);
                    System.out.println(">>> Final payload successfully transferred to beneficiary: " + secret.getBeneficiaryEmail());
                } catch (Exception e) {
                    System.out.println(">>> Error sending mail to beneficiary: " + e.getMessage());
                }
            }
        }
    }
}