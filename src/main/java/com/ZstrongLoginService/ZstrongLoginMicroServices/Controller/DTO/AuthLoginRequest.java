package com.ZstrongLoginService.ZstrongLoginMicroServices.Controller.DTO;

import jakarta.validation.constraints.NotBlank;

/**
 * This is the AuthLoginRequest DTO
 * @Author Santiago Agredo Vallejo
 * @Version 1.0
 */
public record AuthLoginRequest(@NotBlank String username, @NotBlank String password) {
}
