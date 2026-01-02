package com.mvc.userservice.dto;

import com.mvc.userservice.enums.KycStatus;
import com.mvc.userservice.entity.User;
import com.mvc.userservice.enums.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UserResponseDto(UUID id,
//                              UUID keycloakId,
                              String username,
                              String email,
                              String phoneNumber,
                              String adresse,
                              String fillName,
                              String lastName,
                              LocalDateTime createdAt,
                              Status status) {
    public static UserResponseDto fromEntity(User user){
        if(user == null){
            return null;
        }
        return new UserResponseDto(
                user.getId(),
//                user.getKeycloakId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAdresse(),
                user.getFirstName(),
                user.getLastName(),
                user.getCreatedAt(),
                user.getStatus()
        );
    }
}
