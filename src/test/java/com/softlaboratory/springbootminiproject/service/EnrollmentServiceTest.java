package com.softlaboratory.springbootminiproject.service;

import com.softlaboratory.springbootminiproject.domain.common.ApiResponse;
import com.softlaboratory.springbootminiproject.domain.dao.*;
import com.softlaboratory.springbootminiproject.domain.dto.*;
import com.softlaboratory.springbootminiproject.repository.ClassRepository;
import com.softlaboratory.springbootminiproject.repository.EnrollmentRepository;
import com.softlaboratory.springbootminiproject.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EnrollmentService.class)
class EnrollmentServiceTest {

    @MockBean
    private EnrollmentRepository enrollmentRepository;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private ClassRepository classRepository;

    @MockBean
    private ModelMapper mapper;

    @Autowired
    private EnrollmentService enrollmentService;

    @Test
    void enroll_Success_Test() {
        //mock
        EnrollmentDao enrollmentDao = EnrollmentDao.builder()
                .id(1L)
                .studentDao(StudentDao.builder()
                        .id(1L)
                        .build())
                .classDao(ClassDao.builder()
                        .id(1L)
                        .build())
                .build();

        when(enrollmentRepository.save(any())).thenReturn(enrollmentDao);

        //test
        ResponseEntity<Object> responseEntity = enrollmentService.enroll(
                EnrollmentDto.builder()
                        .studentData(StudentDto.builder()
                                .id(1L)
                                .build())
                        .classData(ClassDto.builder()
                                .id(1L)
                                .build())
                        .build()
        );
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        EnrollmentDto resultData = (EnrollmentDto) Objects.requireNonNull(apiResponse).getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void enroll_Exception_Test() {
        //mock
        EnrollmentDao enrollmentDao = EnrollmentDao.builder()
                .id(1L)
                .studentDao(StudentDao.builder()
                        .id(1L)
                        .build())
                .classDao(ClassDao.builder()
                        .id(1L)
                        .build())
                .build();

        when(enrollmentRepository.save(any())).thenThrow(NullPointerException.class);

        //test
        ResponseEntity<Object> responseEntity = enrollmentService.enroll(
                EnrollmentDto.builder()
                        .studentData(StudentDto.builder()
                                .id(1L)
                                .build())
                        .classData(ClassDto.builder()
                                .id(1L)
                                .build())
                        .build()
        );
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        EnrollmentDto resultData = (EnrollmentDto) Objects.requireNonNull(apiResponse).getData();

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void unenroll_Success_Test() {
        //mock
        EnrollmentDao enrollmentDao = EnrollmentDao.builder()
                .id(1L)
                .studentDao(StudentDao.builder()
                        .id(1L)
                        .build())
                .classDao(ClassDao.builder()
                        .id(1L)
                        .build())
                .build();

        when(enrollmentRepository.findById(anyLong())).thenReturn(Optional.of(enrollmentDao));

        //test
        ResponseEntity<Object> responseEntity = enrollmentService.unenroll(anyLong());

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void unenroll_NotFound_Test() {
        //mock
        when(enrollmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        //test
        ResponseEntity<Object> responseEntity = enrollmentService.unenroll(anyLong());

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void unenroll_Exception_Test() {
        //mock
        when(enrollmentRepository.findById(anyLong())).thenThrow(NullPointerException.class);

        //test
        ResponseEntity<Object> responseEntity = enrollmentService.unenroll(anyLong());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void print_Success_Test() {
        //mock
        StudentDao studentDao = StudentDao.builder()
                .id(1L)
                .faculty(FacultyDao.builder()
                        .id(1L)
                        .build())
                .major(MajorDao.builder()
                        .id(1L)
                        .build())
                .build();

        ClassDao classDao = ClassDao.builder()
                .id(1L)
                .course(CourseDao.builder()
                        .id(1L)
                        .build())
                .lecturer(LecturerDao.builder()
                        .id(1L)
                        .faculty(FacultyDao.builder()
                                .id(1L)
                                .build())
                        .build())
                .build();

        ClassDto classDto = ClassDto.builder()
                .id(1L)
                .lecturer(LecturerDto.builder()
                        .id(1L)
                        .faculty(FacultyDto.builder()
                                .id(1L)
                                .build())
                        .build())
                .course(CourseDto.builder()
                        .id(1L)
                        .build())
                .build();

        EnrollmentPrintDto enrollmentPrintDto = EnrollmentPrintDto.builder()
                        .student(StudentDto.builder()
                                .id(1L)
                                .faculty(FacultyDto.builder()
                                        .id(1L)
                                        .build())
                                .major(MajorDto.builder()
                                        .id(1L)
                                        .build())
                                .build())
                        .classList(List.of(classDto))
                .build();

        when(studentRepository.findByNimEquals(anyString())).thenReturn(studentDao);
        when(classRepository.findByEnrollmentDaoList_StudentDao_IdEquals(anyLong())).thenReturn(List.of(classDao));
//        when(mapper.map(any(),eq(ClassDto.class))).thenReturn(classDto);
        when(mapper.map(any(),eq(EnrollmentPrintDto.class))).thenReturn(enrollmentPrintDto);

        //test
        ResponseEntity<Object> responseEntity = enrollmentService.print(anyString());

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void print_Exception_Test() {
        //mock
        when(studentRepository.findByNimEquals(anyString())).thenThrow(NullPointerException.class);

        //test
        ResponseEntity<Object> responseEntity = enrollmentService.print(anyString());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
    }

}