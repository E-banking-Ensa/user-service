package com.mvc.userservice.dto;

import com.mvc.userservice.enums.KycStatus;
import com.mvc.userservice.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UserResponseDto(UUID id,
                              UUID keycloakId,
                              String username,
                              String email,
                              String phoneNumber,
                              String adresse,
                              String fullName,
                              KycStatus kycStatus,
                              LocalDateTime createdAt,
                              boolean enabled) {
    public static UserResponseDto fromEntity(User user){
        if(user == null){
            return null;
        }
//        List<ConsentDto> consentDtos=user.getConsentList().stream()
//                .map(ConsentDto::fromEntity)
//                .toList();
        return new UserResponseDto(
                user.getId(),
                user.getKeycloakId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAdresse(),
                user.getFullName(),
                user.getKycStatus(),
                user.getCreatedAt(),
//                consentDtos,
                user.isEnabled()
        );
    }
}
