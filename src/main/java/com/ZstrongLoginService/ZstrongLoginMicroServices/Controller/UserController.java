package com.ZstrongLoginService.ZstrongLoginMicroServices.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/method")
public class UserController {

    @GetMapping("/Dentro")
    public String login() {
        return "Dentro Success";
    }

    @GetMapping("/DentroAuth")
    public String logout() {
        return "auth Success";
    }
    
}
