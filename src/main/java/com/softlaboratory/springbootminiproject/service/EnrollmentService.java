package com.softlaboratory.springbootminiproject.service;

import com.softlaboratory.springbootminiproject.constant.AppConstant;
import com.softlaboratory.springbootminiproject.domain.dao.ClassDao;
import com.softlaboratory.springbootminiproject.domain.dao.EnrollmentDao;
import com.softlaboratory.springbootminiproject.domain.dao.StudentDao;
import com.softlaboratory.springbootminiproject.domain.dto.*;
import com.softlaboratory.springbootminiproject.repository.ClassRepository;
import com.softlaboratory.springbootminiproject.repository.EnrollmentRepository;
import com.softlaboratory.springbootminiproject.repository.StudentRepository;
import com.softlaboratory.springbootminiproject.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    public ResponseEntity<Object> enroll(EnrollmentDto request) {
        try {
            EnrollmentDao enrollmentDao = EnrollmentDao.builder()
                    .studentDao(StudentDao.builder()
                            .id(request.getStudentData().getId())
                            .build())
                    .classDao(ClassDao.builder()
                            .id(request.getClassData().getId())
                            .build())
                    .build();
            enrollmentRepository.save(enrollmentDao);

            EnrollmentDto enrollmentDto = EnrollmentDto.builder()
                    .id(enrollmentDao.getId())
                    .studentData(StudentDto.builder()
                            .id(enrollmentDao.getStudentDao().getId())
//                            .nim(enrollmentDao.getStudentDao().getNim())
//                            .name(enrollmentDao.getStudentDao().getName())
                            .build())
                    .classData(ClassDto.builder()
                            .id(enrollmentDao.getClassDao().getId())
//                            .code(enrollmentDao.getClassDao().getCode())
//                            .maxStudent(enrollmentDao.getClassDao().getMaxStudent())
//                            .course(CourseDto.builder()
//                                    .id(enrollmentDao.getClassDao().getCourse().getId())
//                                    .code(enrollmentDao.getClassDao().getCourse().getCode())
//                                    .course(enrollmentDao.getClassDao().getCourse().getCourse())
//                                    .credit(enrollmentDao.getClassDao().getCourse().getCredit())
//                                    .build())
                            .build())
                    .build();

            return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_SUCCESS, enrollmentDto);
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, e.getMessage());
        }
    }

    public ResponseEntity<Object> unenroll(Long id) {
        try {
            Optional<EnrollmentDao> enrollmentDao = enrollmentRepository.findById(id);
            if (enrollmentDao.isPresent()) {
                enrollmentRepository.deleteById(id);
                return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_SUCCESS, null);
            }else {
                return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
            }
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

    public ResponseEntity<Object> print(String nim) {
        try {
            StudentDao studentDao = studentRepository.findByNimEquals(nim);
            List<ClassDao> classDaoList = classRepository.findByEnrollmentDaoList_StudentDao_IdEquals(studentDao.getId());
            List<ClassDto> classDtoList = new ArrayList<>();

            for (ClassDao classDao : classDaoList){
                classDtoList.add(ClassDto.builder()
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

            EnrollmentPrintDto enrollmentPrintDto = EnrollmentPrintDto.builder()
                    .student(StudentDto.builder()
                            .id(studentDao.getId())
                            .nim(studentDao.getNim())
                            .name(studentDao.getName())
                            .maxCredit(studentDao.getMaxCredit())
                            .totalCredit(studentDao.getTotalCredit())
                            .faculty(FacultyDto.builder()
                                    .id(studentDao.getFaculty().getId())
                                    .code(studentDao.getFaculty().getCode())
                                    .faculty(studentDao.getFaculty().getFaculty())
                                    .build())
                            .major(MajorDto.builder()
                                    .id(studentDao.getMajor().getId())
                                    .code(studentDao.getMajor().getCode())
                                    .major(studentDao.getMajor().getMajor())
                                    .build())
                            .build())
                    .classList(classDtoList)
                    .build();

            return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_SUCCESS, enrollmentPrintDto);
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

}
