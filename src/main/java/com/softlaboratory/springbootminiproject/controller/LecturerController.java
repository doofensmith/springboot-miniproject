package com.softlaboratory.springbootminiproject.controller;

import com.softlaboratory.springbootminiproject.domain.dto.LecturerDto;
import com.softlaboratory.springbootminiproject.service.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/lecturer")
public class LecturerController {

    @Autowired
    private LecturerService lecturerService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllLecturer() {
        try {
            return lecturerService.getAllLecturer();
        }catch (Exception e) {
            throw e;
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getLecturerById(@PathVariable Long id) {
        try {
            return lecturerService.getLecturerById(id);
        }catch (Exception e) {
            throw e;
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> addNewLecturer(@RequestBody LecturerDto request) {
        try {
            return lecturerService.addNewLecturer(request);
        }catch (Exception e) {
            throw e;
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateLecturer(@PathVariable Long id, @RequestBody LecturerDto request) {
        try {
            return lecturerService.updateLecturer(id, request);
        }catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteLecturer(@PathVariable Long id) {
        try {
            return lecturerService.deleteLecturer(id);
        }catch (Exception e) {
            throw e;
        }
    }

}
