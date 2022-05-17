package com.softlaboratory.springbootminiproject.controller;

import com.softlaboratory.springbootminiproject.domain.dto.FacultyDto;
import com.softlaboratory.springbootminiproject.service.FacultyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(value = "/api/faculty")
public class FacultyController {

    @Autowired
    private FacultyService service;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllFaculty() {

        try {
            return service.getAllFaculty();
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getFacultyById(@PathVariable Long id) {

        try {
            return service.getFacultyById(id);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

    }

    @PostMapping(value = "")
    public ResponseEntity<Object> addNewFaculty(@RequestBody FacultyDto facultyDto) {

        try {
            return service.addNewFaculty(facultyDto);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateFaculty(@PathVariable Long id, @RequestBody FacultyDto facultyDto) {
        try {
            return service.updateFaculty(id, facultyDto);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteFaculty(@PathVariable Long id) {

        try {
            return service.deleteFaculty(id);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

    }
}
