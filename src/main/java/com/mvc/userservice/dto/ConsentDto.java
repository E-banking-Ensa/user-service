package com.mvc.userservice.dto;

import com.mvc.userservice.entity.Consent;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConsentDto(UUID consentId,
                         String  consentType,//ici c'est le nom du type de consentement et non plus l'enum
                         boolean isOk,//si le consementent ets encore accord ou bien descative
                         LocalDateTime grantedAt,
                         LocalDateTime revokedAt) {
    public static ConsentDto fromEntity(Consent consent){
        return new ConsentDto(
                consent.getId(),
                consent.getConsentType().getName(),
                consent.isOk(),
                consent.getGrantedAt(),
                consent.getRevokedAt()
        );
    }
}
