package com.softlaboratory.springbootminiproject.controller;

import com.softlaboratory.springbootminiproject.constant.AppConstant;
import com.softlaboratory.springbootminiproject.domain.dto.UserDto;
import com.softlaboratory.springbootminiproject.service.AuthService;
import com.softlaboratory.springbootminiproject.util.ResponseUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/register")
    public ResponseEntity<Object> createUser(@RequestBody UserDto request) {
        try {
            log.debug("REQUEST:"+request.getUsername()+","+request.getPassword());
            return authService.register(request);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody UserDto request) {
        try {
            log.debug("REQUEST:"+request.getUsername()+","+request.getPassword());
            return authService.login(request);
        }catch (Exception e) {
            log.error(e.getMessage());
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, e.getMessage());
        }
    }

}
