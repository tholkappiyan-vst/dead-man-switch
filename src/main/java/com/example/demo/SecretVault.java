package com.example.demo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "secrets")
public class SecretVault {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String secretData;
    
    private String beneficiaryEmail;
    private int intervalMinutes;
    private LocalDateTime lastCheckIn;
    private String status; // ACTIVE, PENDING_PROOF, RELEASED

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSecretData() { return secretData; }
    public void setSecretData(String secretData) { this.secretData = secretData; }

    public String getBeneficiaryEmail() { return beneficiaryEmail; }
    public void setBeneficiaryEmail(String beneficiaryEmail) { this.beneficiaryEmail = beneficiaryEmail; }

    public int getIntervalMinutes() { return intervalMinutes; }
    public void setIntervalMinutes(int intervalMinutes) { this.intervalMinutes = intervalMinutes; }

    public LocalDateTime getLastCheckIn() { return lastCheckIn; }
    public void setLastCheckIn(LocalDateTime lastCheckIn) { this.lastCheckIn = lastCheckIn; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
