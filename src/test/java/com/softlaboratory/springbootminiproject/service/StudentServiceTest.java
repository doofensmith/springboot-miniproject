package com.softlaboratory.springbootminiproject.service;

import com.softlaboratory.springbootminiproject.domain.common.ApiResponse;
import com.softlaboratory.springbootminiproject.domain.dao.StudentDao;
import com.softlaboratory.springbootminiproject.domain.dto.StudentDto;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StudentService.class)
class StudentServiceTest {

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private ModelMapper mapper;

    @Autowired
    private StudentService studentService;

    @Test
    void getAllStudentNotNull_Success_Test() {

        //mock
        StudentDao studentDao = StudentDao.builder()
                .id(1L)
                .name("Test")
                .build();

        when(studentRepository.findAll()).thenReturn(List.of(studentDao));
        when(mapper.map(any(), eq(StudentDto.class))).thenReturn(
                StudentDto.builder()
                        .id(1L)
                        .name("Test")
                        .build()
        );

        //test service
        ResponseEntity<Object> responseEntity = studentService.getAllStudent();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<StudentDto> studentDtoList = (List<StudentDto>) apiResponse.getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(1, studentDtoList.size());

    }

    @Test
    void getAllStudentNull_Success_Test() {
        //mock
        StudentDto studentDto = new StudentDto();


        when(mapper.map(any(),eq(StudentDto.class))).thenReturn(studentDto);

        //test service
        ResponseEntity<Object> responseEntity = studentService.getAllStudent();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<StudentDto> studentDtoList = (List<StudentDto>) apiResponse.getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(0, studentDtoList.size());

    }

    @Test
    void getAllStudentError_Exception_Test() {

        //mock
        when(studentService.getAllStudent()).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = studentService.getAllStudent();

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void getStudentByIdNotNull_Success_Test() {
        //mocking
        StudentDao studentDao = StudentDao.builder()
                .id(1L)
                .build();

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(studentDao));
        when(mapper.map(any(), eq(StudentDto.class))).thenReturn(StudentDto.builder()
                .id(1L)
                .build());

        //test service
        ResponseEntity<Object> responseEntity = studentService.getStudentById(anyLong());

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void getStudentByIdNotFound_Test() {

        //test service
        ResponseEntity<Object> responseEntity = studentService.getStudentById(anyLong());

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void getStudentById_Error_Test() {

        //mocking
        when(studentService.getStudentById(anyLong())).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = studentService.getStudentById(anyLong());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void addNewStudentWithPayload_Success_Test() {
        //mocking
        StudentDao studentDao = StudentDao.builder()
                .id(1L)
                .build();

        //payload
        StudentDto studentDto = StudentDto.builder()
                .id(1L)
                .name("Test")
                .build();

        when(mapper.map(any(),eq(StudentDao.class))).thenReturn(studentDao);
        when(mapper.map(any(),eq(StudentDto.class))).thenReturn(studentDto);
        when(studentRepository.save(any())).thenReturn(studentDao);

        //test service
        ResponseEntity<Object> responseEntity = studentService.addNewStudent(
                StudentDto.builder()
                        .id(1L)
                        .name("Test")
                        .build()
        );
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        StudentDto resultData = (StudentDto) Objects.requireNonNull(apiResponse).getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(studentDto.getName(), resultData.getName());

    }

    @Test
    void addNewStudentException_Error_Test() {
        //mocking
        when(studentService.addNewStudent(mapper.map(any(),eq(StudentDto.class)))).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = studentService.addNewStudent(StudentDto.builder().build());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void updateStudent_Success_Test() {
        //mocking
        StudentDao studentDao = StudentDao.builder()
                .id(1L)
                .name("Test")
                .build();

        StudentDto studentDto = StudentDto.builder()
                .id(1L)
                .name("Update Test")
                .build();

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(studentDao));
        when(mapper.map(any(),eq(StudentDao.class))).thenReturn(studentDao);
        when(mapper.map(any(),eq(StudentDto.class))).thenReturn(studentDto);

        //test service
        ResponseEntity<Object> responseEntity = studentService.updateStudent(
                anyLong(),
                StudentDto.builder()
                        .name("Update Test")
                        .build()
        );

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        StudentDto resultData = (StudentDto) apiResponse.getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(studentDto.getName(),resultData.getName());

    }

    @Test
    void updateStudentNotFound_Failed_Test() {
        //mocking
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        //test service
        ResponseEntity<Object> responseEntity = studentService.updateStudent(
                anyLong(),
                StudentDto.builder()
                        .name("Update Test")
                        .build()
        );

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void updateStudentException_Error_Test() {
        //mocking
        when(studentRepository.save(any())).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = studentService.updateStudent(
                anyLong(),
                StudentDto.builder()
                        .name("Update Test")
                        .build()
        );

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void deleteStudent_Success_Test() {
        //mocking
        StudentDao studentDao = StudentDao.builder()
                .id(1L)
                .build();

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(studentDao));

        //test service
        ResponseEntity<Object> responseEntity = studentService.deleteStudent(anyLong());

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void deleteStudentIdNotFound_Failed_Test() {
        //mocking
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        //test service
        ResponseEntity<Object> responseEntity = studentService.deleteStudent(anyLong());

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void deleteStudentException_Error_Test() {
        //mocking
        when(studentService.deleteStudent(anyLong())).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = studentService.deleteStudent( anyLong());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
    }

}