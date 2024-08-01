package com.ZstrongLoginService.ZstrongLoginMicroServices.Controller.DTO;

import jakarta.validation.constraints.NotBlank;

/**
 * This is the AuthCreateUser DTO
 * @Author Santiago Agredo Vallejo
 * @Version 1.0
 */
public record AuthCreateUser(@NotBlank String username, 
                             @NotBlank String password,
                             @NotBlank String email,
                             AuthCreateRoleRequest roleRequest) {
} 
