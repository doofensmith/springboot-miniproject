package com.softlaboratory.springbootminiproject.controller;

import com.softlaboratory.springbootminiproject.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping(value = "/test")
    public ResponseEntity<Object> getAllTestData() {
        try {
            return testService.getAllTestData();
        }catch (Exception e) {
            throw e;
        }
    }

}
