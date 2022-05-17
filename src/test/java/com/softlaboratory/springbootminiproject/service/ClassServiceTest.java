package com.softlaboratory.springbootminiproject.service;

import com.softlaboratory.springbootminiproject.domain.common.ApiResponse;
import com.softlaboratory.springbootminiproject.domain.dao.ClassDao;
import com.softlaboratory.springbootminiproject.domain.dao.CourseDao;
import com.softlaboratory.springbootminiproject.domain.dao.FacultyDao;
import com.softlaboratory.springbootminiproject.domain.dao.LecturerDao;
import com.softlaboratory.springbootminiproject.domain.dto.ClassDto;
import com.softlaboratory.springbootminiproject.domain.dto.CourseDto;
import com.softlaboratory.springbootminiproject.domain.dto.LecturerDto;
import com.softlaboratory.springbootminiproject.repository.ClassRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ClassService.class)
class ClassServiceTest {

    @MockBean
    private ClassRepository classRepository;

    @MockBean
    private ModelMapper mapper;

    @Autowired
    private ClassService classService;

    @Test
    void getAllClassNotNull_Success_Test() {

        //mock
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

        when(classRepository.findAll()).thenReturn(List.of(classDao));

        //test service
        ResponseEntity<Object> responseEntity = classService.getAllClass();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void getAllClassError_Exception_Test() {

        //mock
        when(classRepository.findAll()).thenThrow(NullPointerException.class);

        //test service
        ResponseEntity<Object> responseEntity = classService.getAllClass();

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void getClassByIdNotNull_Success_Test() {
        //mocking
        ClassDao classDao = ClassDao.builder()
                .id(1L)
                .course(CourseDao.builder()
                        .id(1L)
                        .build())
                .lecturer(LecturerDao.builder()
                        .id(1L)
                        .build())
                .build();

        when(classRepository.findById(anyLong())).thenReturn(Optional.of(classDao));

        //test service
        ResponseEntity<Object> responseEntity = classService.getClassById(anyLong());

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void getClassByIdNotFound_Test() {
        //mock
        when(classRepository.findById(anyLong())).thenReturn(Optional.empty());

        //test service
        ResponseEntity<Object> responseEntity = classService.getClassById(anyLong());

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void getClassById_Error_Test() {

        //mocking
        when(classRepository.findById(anyLong())).thenThrow(NullPointerException.class);

        //test service
        ResponseEntity<Object> responseEntity = classService.getClassById(anyLong());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void createNewClass_Success_Test() {
        //mocking
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

        when(classRepository.save(any())).thenReturn(classDao);

        //test service
        ResponseEntity<Object> responseEntity = classService.createNewClass(
                ClassDto.builder()
                        .id(1L)
                        .course(CourseDto.builder()
                                .id(1L)
                                .build())
                        .lecturer(LecturerDto.builder()
                                .id(1L)
                                .build())
                        .build()
        );

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void createNewClassException_Error_Test() {
        //mocking
        when(classRepository.save(any())).thenThrow(NullPointerException.class);

        //test service
        ResponseEntity<Object> responseEntity = classService.createNewClass(ClassDto.builder().build());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void updateClass_Success_Test() {
        //mocking
        ClassDao classDao = ClassDao.builder()
                .id(1L)
                .code("Test")
                .build();

        ClassDto classDto = ClassDto.builder()
                .id(1L)
                .code("Update Test")
                .build();

        when(classRepository.findById(anyLong())).thenReturn(Optional.of(classDao));
        when(mapper.map(any(),eq(ClassDao.class))).thenReturn(classDao);
        when(mapper.map(any(),eq(ClassDto.class))).thenReturn(classDto);

        //test service
        ResponseEntity<Object> responseEntity = classService.updateClass(
                anyLong(),
                ClassDto.builder()
                        .id(1L)
                        .code("Update Test")
                        .course(CourseDto.builder()
                                .id(1L)
                                .build())
                        .lecturer(LecturerDto.builder()
                                .id(1L)
                                .build())
                        .build()
        );

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        ClassDto resultData = (ClassDto) apiResponse.getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(classDto.getCode(),resultData.getCode());

    }

    @Test
    void updateClassNotFound_Failed_Test() {
        //mocking
        when(classRepository.findById(anyLong())).thenReturn(Optional.empty());

        //test service
        ResponseEntity<Object> responseEntity = classService.updateClass(
                anyLong(),
                ClassDto.builder()
                        .code("Update Test")
                        .build()
        );

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void updateClassException_Error_Test() {
        //mocking
        when(classRepository.save(any())).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = classService.updateClass(
                anyLong(),
                ClassDto.builder()
                        .code("Update Test")
                        .build()
        );

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void deleteClass_Success_Test() {
        //mocking
        ClassDao classDao = ClassDao.builder()
                .id(1L)
                .build();

        when(classRepository.findById(anyLong())).thenReturn(Optional.of(classDao));

        //test service
        ResponseEntity<Object> responseEntity = classService.deleteClass(anyLong());

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void deleteClassIdNotFound_Failed_Test() {
        //mocking
        when(classRepository.findById(anyLong())).thenReturn(Optional.empty());

        //test service
        ResponseEntity<Object> responseEntity = classService.deleteClass(anyLong());

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void deleteClassException_Error_Test() {
        //mocking
        when(classService.deleteClass(anyLong())).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = classService.deleteClass(anyLong());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
    }

}