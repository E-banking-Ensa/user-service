package com.mvc.userservice.dto;

public record UpdateUserRequestDto(
        String firstName,
        String lastName,
        String phoneNumber,
        String address,
        String email
) {
}
