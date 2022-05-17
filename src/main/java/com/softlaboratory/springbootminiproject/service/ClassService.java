package com.softlaboratory.springbootminiproject.service;

import com.softlaboratory.springbootminiproject.constant.AppConstant;
import com.softlaboratory.springbootminiproject.domain.dao.ClassDao;
import com.softlaboratory.springbootminiproject.domain.dao.CourseDao;
import com.softlaboratory.springbootminiproject.domain.dao.LecturerDao;
import com.softlaboratory.springbootminiproject.domain.dto.ClassDto;
import com.softlaboratory.springbootminiproject.domain.dto.CourseDto;
import com.softlaboratory.springbootminiproject.domain.dto.FacultyDto;
import com.softlaboratory.springbootminiproject.domain.dto.LecturerDto;
import com.softlaboratory.springbootminiproject.repository.ClassRepository;
import com.softlaboratory.springbootminiproject.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    public ResponseEntity<Object> getAllClass() {
        try {
            List<ClassDao> classDaoList = classRepository.findAll();
            List<ClassDto> classDtoList = new ArrayList<>();
            for (ClassDao classDao : classDaoList) {
                classDtoList.add(
                        ClassDto.builder()
                                .id(classDao.getId())
                                .code(classDao.getCode())
                                .maxStudent(classDao.getMaxStudent())
                                .lecturer(LecturerDto.builder()
                                        .id(classDao.getLecturer().getId())
                                        .nidn(classDao.getLecturer().getNidn())
                                        .name(classDao.getLecturer().getName())
                                        .faculty(FacultyDto.builder()
                                                .id(classDao.getLecturer().getFaculty().getId())
                                                .code(classDao.getLecturer().getFaculty().getCode())
                                                .faculty(classDao.getLecturer().getFaculty().getFaculty())
                                                .build())
                                        .build())
                                .course(CourseDto.builder()
                                        .id(classDao.getCourse().getId())
                                        .code(classDao.getCourse().getCode())
                                        .credit(classDao.getCourse().getCredit())
                                        .course(classDao.getCourse().getCourse())
                                        .build())
                                .build()
                );
            }
            return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_SUCCESS, classDtoList);
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

    public ResponseEntity<Object> getClassById(Long id) {
        try {
            Optional<ClassDao> classDao = classRepository.findById(id);
            if (classDao.isPresent()) {
                ClassDto classDto = ClassDto.builder()
                        .id(classDao.get().getId())
                        .code(classDao.get().getCode())
                        .maxStudent(classDao.get().getMaxStudent())
                        .lecturer(LecturerDto.builder()
                                .id(classDao.get().getLecturer().getId())
                                .nidn(classDao.get().getLecturer().getNidn())
                                .name(classDao.get().getLecturer().getName())
                                .build())
                        .course(CourseDto.builder()
                                .id(classDao.get().getCourse().getId())
                                .code(classDao.get().getCourse().getCode())
                                .credit(classDao.get().getCourse().getCredit())
                                .course(classDao.get().getCourse().getCourse())
                                .build())
                        .build();
                return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_SUCCESS, classDto);
            }else {
                return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
            }
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

    public ResponseEntity<Object> createNewClass(ClassDto request) {
        try {
            ClassDao classDao = ClassDao.builder()
                    .id(request.getId())
                    .code(request.getCode())
                    .maxStudent(request.getMaxStudent())
                    .lecturer(LecturerDao.builder()
                            .id(request.getLecturer().getId())
                            .build())
                    .course(CourseDao.builder()
                            .id(request.getCourse().getId())
                            .build())
                    .build();
            classRepository.save(classDao);

            ClassDto classDto = ClassDto.builder()
                    .id(classDao.getId())
                    .code(classDao.getCode())
                    .maxStudent(classDao.getMaxStudent())
                    .lecturer(LecturerDto.builder()
                            .name(classDao.getLecturer().getName())
                            .build())
                    .course(CourseDto.builder()
                            .course(classDao.getCourse().getCourse())
                            .build())
                    .build();

            return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_CREATED, classDto);
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

    public ResponseEntity<Object> updateClass(Long id, ClassDto request) {
        try {
            Optional<ClassDao> classDaoOld = classRepository.findById(id);
            if (classDaoOld.isPresent()) {

                ClassDao classDaoNew = classDaoOld.get();
                classDaoNew.setCode(request.getCode());
                classDaoNew.setMaxStudent(request.getMaxStudent());
                classDaoNew.setLecturer(LecturerDao.builder()
                        .id(request.getLecturer().getId())
                        .build());
                classDaoNew.setCourse(CourseDao.builder()
                        .id(request.getCourse().getId())
                        .build());
                classRepository.save(classDaoNew);

                ClassDto classDto = ClassDto.builder()
                        .id(classDaoNew.getId())
                        .code(classDaoNew.getCode())
                        .maxStudent(classDaoNew.getMaxStudent())
                        .lecturer(LecturerDto.builder()
                                .name(classDaoNew.getLecturer().getName())
                                .build())
                        .course(CourseDto.builder()
                                .course(classDaoNew.getCourse().getCourse())
                                .build())
                        .build();

                return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_UPDATED, classDto);
            }else {
                return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
            }
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

    public ResponseEntity<Object> deleteClass(Long id) {
        try {
            Optional<ClassDao> classDao = classRepository.findById(id);
            if (classDao.isPresent()) {
                classRepository.deleteById(id);
                return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_DELETED, null);
            }else {
                return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
            }
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

}
