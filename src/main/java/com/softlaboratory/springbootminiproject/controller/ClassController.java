package com.softlaboratory.springbootminiproject.controller;

import com.softlaboratory.springbootminiproject.domain.dto.ClassDto;
import com.softlaboratory.springbootminiproject.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/class")
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllClass() {
        try {
            return classService.getAllClass();
        }catch (Exception e) {
            throw e;
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getClassById(@PathVariable Long id) {
        try {
            return classService.getClassById(id);
        }catch (Exception e) {
            throw e;
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewClass(@RequestBody ClassDto request) {
        try {
            return classService.createNewClass(request);
        }catch (Exception e) {
            throw e;
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateClass(@PathVariable Long id, @RequestBody ClassDto request) {
        try {
            return classService.updateClass(id, request);
        }catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteClass(@PathVariable Long id) {
        try {
            return classService.deleteClass(id);
        }catch (Exception e) {
            throw e;
        }
    }

}
