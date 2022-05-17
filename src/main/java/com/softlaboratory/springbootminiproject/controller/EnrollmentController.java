package com.softlaboratory.springbootminiproject.controller;

import com.softlaboratory.springbootminiproject.domain.dto.EnrollmentDto;
import com.softlaboratory.springbootminiproject.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            throw e;
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> unenroll(@PathVariable Long id) {
        try {
            return enrollmentService.unenroll(id);
        }catch (Exception e) {
            throw e;
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> print(@RequestParam(value = "student_nim") String nim) {
        try {
            return enrollmentService.print(nim);
        }catch (Exception e) {
            throw e;
        }
    }

}
