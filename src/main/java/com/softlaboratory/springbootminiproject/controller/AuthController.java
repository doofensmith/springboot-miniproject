package com.softlaboratory.springbootminiproject.controller;

import com.softlaboratory.springbootminiproject.domain.dto.UserDto;
import com.softlaboratory.springbootminiproject.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/register")
    public ResponseEntity<Object> createUser(@RequestBody UserDto request) {
        try {
            return authService.register(request);
        }catch (Exception e) {
            throw e;
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody UserDto request) {
        try {
            return authService.login(request);
        }catch (Exception e) {
            throw e;
        }
    }

}
