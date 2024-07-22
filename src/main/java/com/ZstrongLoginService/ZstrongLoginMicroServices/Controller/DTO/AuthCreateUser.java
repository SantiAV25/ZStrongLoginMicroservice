package com.ZstrongLoginService.ZstrongLoginMicroServices.Controller.DTO;

import jakarta.validation.constraints.NotBlank;

public record AuthCreateUser(@NotBlank String username, 
                             @NotBlank String password,
                             AuthCreateRoleRequest roleRequest) {
} 
