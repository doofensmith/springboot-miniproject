package com.softlaboratory.springbootminiproject.controller;

import com.softlaboratory.springbootminiproject.domain.dto.MajorDto;
import com.softlaboratory.springbootminiproject.service.MajorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(value = "/api/major")
public class MajorController {

    @Autowired
    private MajorService majorService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllMajor() {
        try {
            return majorService.getAllMajor();
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getMajorById(@PathVariable Long id) {
        try {
            return majorService.getMajorById(id);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> addMajor(@RequestBody MajorDto request) {
        try {
            return majorService.addMajor(request);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateMajor(@PathVariable Long id, @RequestBody MajorDto request) {
        try {
            return majorService.updateMajor(id, request);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteMajor(@PathVariable Long id) {
        try {
            return majorService.deleteMajor(id);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

}
