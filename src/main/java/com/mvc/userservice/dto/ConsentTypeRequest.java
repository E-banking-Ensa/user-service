package com.mvc.userservice.dto;

import jakarta.validation.constraints.NotNull;

public record ConsentTypeRequest(@NotNull String code,
                                 @NotNull String name)
{ }
