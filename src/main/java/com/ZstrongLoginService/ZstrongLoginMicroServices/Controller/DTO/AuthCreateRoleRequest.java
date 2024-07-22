package com.ZstrongLoginService.ZstrongLoginMicroServices.Controller.DTO;
import java.util.List;

import jakarta.validation.constraints.Size;

/**
 * This is the AuthCreateRoleRequest DTO
 * @Author Santiago Agredo Vallejo
 * @Version 1.0
 */
public record AuthCreateRoleRequest(
    @Size(max = 3, message = "The user cannot have more than 3 roles")List<String> roles) {
    
}
