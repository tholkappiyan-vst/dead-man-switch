package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SecretRepository extends JpaRepository<SecretVault, Long> {
    // This is the missing query method that fixed your compilation error!
    List<SecretVault> findByUserEmail(String userEmail);
}
