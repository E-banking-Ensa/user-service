package com.mvc.userservice.dto;

import com.mvc.userservice.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateUserRequestDto(
//                                   @NotNull UUID keycloakId,
                                   @NotBlank String username,
                                   @NotBlank @Email String email,
                                   int age,
                                   @NotBlank String firstName,
                                   @NotBlank String lastName,
                                   @NotBlank String phoneNumber,
                                   @NotBlank UserRole role,
                                   String address) {
}
