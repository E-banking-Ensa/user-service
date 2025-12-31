package com.mvc.userservice.dto;

import com.mvc.userservice.entity.ConsentType;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConsentTypeDto(UUID consentTypeId,
                             String code,
                             String name,
                             boolean isActive,
                             int nbr,//nombre d'activations
                             LocalDateTime grantedAt,
                             LocalDateTime updatedAt) {
    public static ConsentTypeDto fromEntity(ConsentType consentType) {
        return new ConsentTypeDto(
                consentType.getId(),
                consentType.getCode(),
                consentType.getName(),
                consentType.isActive(),
                consentType.getNbr(),
                consentType.getCreatedAt(),
                consentType.getUpdatedAt()
        );
    }
}
