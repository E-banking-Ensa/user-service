package com.mvc.userservice.dto;

public record UpdateUserRequestDto(
        String fullName,
        String phoneNumber,
        String address
) {
}
