package com.mvc.userservice.repository;

import com.mvc.userservice.entity.Consent;
//import com.mvc.userservice.enums.ConsentType;
import com.mvc.userservice.entity.ConsentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConsentRepository extends JpaRepository<Consent, UUID> {
    List<Consent> findAllByClientId(UUID userId);
    Consent findByClientIdAndConsentType(UUID userId, ConsentType consentType);
}
