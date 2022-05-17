package com.softlaboratory.springbootminiproject.service;

import com.softlaboratory.springbootminiproject.constant.AppConstant;
import com.softlaboratory.springbootminiproject.domain.dao.CourseDao;
import com.softlaboratory.springbootminiproject.domain.dto.CourseDto;
import com.softlaboratory.springbootminiproject.repository.CourseRepository;
import com.softlaboratory.springbootminiproject.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public ResponseEntity<Object> getAllCourse() {
        try {
            List<CourseDao> courseDaoList = courseRepository.findAll();
            List<CourseDto> courseDtoList = new ArrayList<>();
            for (CourseDao courseDao : courseDaoList) {
                courseDtoList.add(
                        CourseDto.builder()
                                .id(courseDao.getId())
                                .code(courseDao.getCode())
                                .course(courseDao.getCourse())
                                .credit(courseDao.getCredit())
                                .build()
                );
            }
            return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_SUCCESS, courseDtoList);
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

    public ResponseEntity<Object> getCourseById(Long id) {
        try {
            Optional<CourseDao> courseDao = courseRepository.findById(id);
            if (courseDao.isPresent()) {
                CourseDto courseDto = CourseDto.builder()
                        .id(courseDao.get().getId())
                        .code(courseDao.get().getCode())
                        .course(courseDao.get().getCourse())
                        .credit(courseDao.get().getCredit())
                        .build();
                return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_SUCCESS, courseDto);
            }else {
                return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
            }
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

    public ResponseEntity<Object> createNewCourse(CourseDto request) {
        try {
            CourseDao courseDao = CourseDao.builder()
                    .code(request.getCode())
                    .course(request.getCourse())
                    .credit(request.getCredit())
                    .build();
            courseRepository.save(courseDao);

            CourseDto courseDto = CourseDto.builder()
                    .id(courseDao.getId())
                    .code(courseDao.getCode())
                    .course(courseDao.getCourse())
                    .credit(courseDao.getCredit())
                    .build();

            return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_CREATED, courseDto);

        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

    public ResponseEntity<Object> updateCourse(Long id, CourseDto request) {
        try {
            Optional<CourseDao> courseDaoOld = courseRepository.findById(id);
            if (courseDaoOld.isPresent()) {
                CourseDao courseDaoNew = courseDaoOld.get();
                courseDaoNew.setCode(request.getCode());
                courseDaoNew.setCourse(request.getCourse());
                courseDaoNew.setCredit(request.getCredit());
                courseRepository.save(courseDaoNew);

                CourseDto courseDto = CourseDto.builder()
                        .id(courseDaoNew.getId())
                        .code(courseDaoNew.getCode())
                        .course(courseDaoNew.getCourse())
                        .credit(courseDaoNew.getCredit())
                        .build();

                return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_UPDATED, courseDto);

            }else {
                return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
            }
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

    public ResponseEntity<Object> deleteCourse(Long id) {
        try {
            Optional<CourseDao> courseDao = courseRepository.findById(id);
            if (courseDao.isPresent()) {
                courseRepository.deleteById(id);
                return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_DELETED, null);
            }else {
                return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
            }
         }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

}
