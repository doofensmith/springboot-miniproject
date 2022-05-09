package com.softlaboratory.springbootminiproject.service;

import com.softlaboratory.springbootminiproject.constant.AppConstant;
import com.softlaboratory.springbootminiproject.domain.dao.StudentDao;
import com.softlaboratory.springbootminiproject.domain.dto.StudentDto;
import com.softlaboratory.springbootminiproject.repository.StudentRepository;
import com.softlaboratory.springbootminiproject.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public ResponseEntity<Object> getAllStudent() {
        try {
            List<StudentDao> studentDaoList = studentRepository.findAll();
            List<StudentDto> studentDtoList = new ArrayList<>();
            for (StudentDao studentDao : studentDaoList) {
                studentDtoList.add(
                        StudentDto.builder()
                                .id(studentDao.getId())
                                .nim(studentDao.getNim())
                                .name(studentDao.getName())
                                .build()
                );
            }
            return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_SUCCESS, studentDtoList);
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

    public ResponseEntity<Object> getStudentById(Long id) {
        try {
            Optional<StudentDao> studentDao = studentRepository.findById(id);
            if (studentDao.isPresent()) {
                StudentDto studentDto = StudentDto.builder()
                        .id(studentDao.get().getId())
                        .nim(studentDao.get().getNim())
                        .name(studentDao.get().getName())
                        .build();
                return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_SUCCESS, studentDto);
            }else {
                return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
            }
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

    public ResponseEntity<Object> addNewStudent(StudentDto request) {
        try {
            StudentDao studentDao = StudentDao.builder()
                    .nim(request.getNim())
                    .name(request.getName())
                    .build();
            studentRepository.save(studentDao);

            StudentDto studentDto = StudentDto.builder()
                    .id(studentDao.getId())
                    .nim(studentDao.getNim())
                    .name(studentDao.getName())
                    .build();

            return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_CREATED, studentDto);

        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

    public ResponseEntity<Object> updateStudent(Long id, StudentDto request) {
        try {
            Optional<StudentDao> studentDaoOld = studentRepository.findById(id);
            if (studentDaoOld.isPresent()) {
                StudentDao studentDaoNew = studentDaoOld.get();
                studentDaoNew.setNim(request.getNim());
                studentDaoNew.setName(request.getName());
                studentRepository.save(studentDaoNew);

                StudentDto studentDto = StudentDto.builder()
                        .id(studentDaoNew.getId())
                        .nim(studentDaoNew.getNim())
                        .name(studentDaoNew.getName())
                        .build();

                return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_UPDATED, studentDto);

            }else {
                return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
            }
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

    public ResponseEntity<Object> deleteStudent(Long id) {
        try {
            Optional<StudentDao> studentDao = studentRepository.findById(id);
            if (studentDao.isPresent()) {
                studentRepository.deleteById(id);
                return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_DELETED, null);
            }else {
                return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
            }
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

}
