package com.softlaboratory.springbootminiproject.controller;

import com.softlaboratory.springbootminiproject.domain.dto.EnrollmentDto;
import com.softlaboratory.springbootminiproject.service.EnrollmentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(value = "/api/enrollment")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping(value = "")
    public ResponseEntity<Object> enroll(@RequestBody EnrollmentDto request) {
        try {
            return enrollmentService.enroll(request);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> unenroll(@PathVariable Long id) {
        try {
            return enrollmentService.unenroll(id);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> print(@RequestParam(value = "student_nim") String nim) {
        try {
            return enrollmentService.print(nim);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

}
