package com.softlaboratory.springbootminiproject.controller;

import com.softlaboratory.springbootminiproject.domain.dto.CourseDto;
import com.softlaboratory.springbootminiproject.service.CourseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(value = "/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllCourse() {
        try {
            return courseService.getAllCourse();
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getCourseById(@PathVariable Long id) {
        try {
            return courseService.getCourseById(id);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewCourse(@RequestBody CourseDto request) {
        try {
            return courseService.createNewCourse(request);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateCourse(@PathVariable Long id, @RequestBody CourseDto request) {
        try {
            return courseService.updateCourse(id, request);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteCourse(@PathVariable Long id) {
        try {
            return courseService.deleteCourse(id);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

}
