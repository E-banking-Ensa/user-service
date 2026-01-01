package com.mvc.userservice.dto;

import com.mvc.userservice.entity.Client;
import com.mvc.userservice.enums.KycStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClientDto(
        UUID clientId,
        UUID keycloakId,
        String username,
        String email,
        String phoneNumber,
        String adresse,
        String fullName,
        KycStatus kycStatus,
        LocalDateTime createdAt,
        boolean enabled
) {
    public static ClientDto fromEntity(Client client){
            return new ClientDto(
                    client.getId(),
                    client.getKeycloakId(),
                    client.getUsername(),
                    client.getEmail(),
                    client.getPhoneNumber(),
                    client.getAdresse(),
                    client.getFullName(),
                    client.getKycStatus(),
                    client.getCreatedAt(),
                    client.isEnabled()
            );
    }
}
