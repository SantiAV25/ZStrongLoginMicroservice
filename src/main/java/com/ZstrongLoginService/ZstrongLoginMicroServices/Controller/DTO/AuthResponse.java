package com.ZstrongLoginService.ZstrongLoginMicroServices.Controller.DTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * This is the AuthResponse DTO
 * @Author Santiago Agredo Vallejo
 * @Version 1.0
 */
@JsonPropertyOrder({"username", "message", "jwt", "status"})
public record AuthResponse(String username, String message, String jwt, boolean status) {
    
}
