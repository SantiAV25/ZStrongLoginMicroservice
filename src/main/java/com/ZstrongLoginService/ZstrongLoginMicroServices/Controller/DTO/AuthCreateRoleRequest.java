package com.ZstrongLoginService.ZstrongLoginMicroServices.Controller.DTO;
import java.util.List;

import jakarta.validation.constraints.Size;

public record AuthCreateRoleRequest(
    @Size(max = 3, message = "The user cannot have more than 3 roles")List<String> roles) {
    
}
