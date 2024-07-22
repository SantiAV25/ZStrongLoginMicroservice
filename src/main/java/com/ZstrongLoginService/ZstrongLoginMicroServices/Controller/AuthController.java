package com.ZstrongLoginService.ZstrongLoginMicroServices.Controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ZstrongLoginService.ZstrongLoginMicroServices.Controller.DTO.AuthCreateUser;
import com.ZstrongLoginService.ZstrongLoginMicroServices.Controller.DTO.AuthLoginRequest;
import com.ZstrongLoginService.ZstrongLoginMicroServices.Controller.DTO.AuthResponse;
import com.ZstrongLoginService.ZstrongLoginMicroServices.persistence.entity.UserEntity;
import com.ZstrongLoginService.ZstrongLoginMicroServices.service.UserDatailsServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDatailsServiceImpl userDetailServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest authLoginRequest) {
        System.out.println("Entro al login");
        System.out.println(authLoginRequest.toString());
        return new ResponseEntity<>(this.userDetailServiceImpl.loginUser(authLoginRequest), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserEntity> signUp(@RequestBody @Valid AuthCreateUser authCreateUser) {
        System.out.println("Entro al sign-up");
        return new ResponseEntity<>(this.userDetailServiceImpl.crateUser(authCreateUser), HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test() {
        return "Test Success";
    }
    
}