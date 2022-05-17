package com.softlaboratory.springbootminiproject.controller;

import com.softlaboratory.springbootminiproject.domain.dto.StudentDto;
import com.softlaboratory.springbootminiproject.service.StudentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(value = "/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllStudent() {
        try {
            return studentService.getAllStudent();
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getStudentById(@PathVariable Long id) {
        try {
            return studentService.getStudentById(id);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> addNewStudent(@RequestBody StudentDto request) {
        try {
            return studentService.addNewStudent(request);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateStudent(@PathVariable Long id, @RequestBody StudentDto request) {
        try {
            return studentService.updateStudent(id, request);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable Long id) {
        try {
            return studentService.deleteStudent(id);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

}
