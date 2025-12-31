package com.mvc.userservice.event;

import com.mvc.userservice.dto.CreateUserRequestDto;

public record UserCreatedEvent(CreateUserRequestDto dto) {}