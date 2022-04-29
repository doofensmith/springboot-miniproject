package com.softlaboratory.springbootminiproject.service;

import com.softlaboratory.springbootminiproject.domain.common.ApiResponse;
import com.softlaboratory.springbootminiproject.domain.dao.CourseDao;
import com.softlaboratory.springbootminiproject.domain.dto.CourseDto;
import com.softlaboratory.springbootminiproject.repository.CourseRepository;
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
@SpringBootTest(classes = CourseService.class)
class CourseServiceTest {

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private ModelMapper mapper;

    @Autowired
    private CourseService courseService;

    @Test
    void getAllCourseNotNull_Success_Test() {

        //mock
        CourseDao courseDao = CourseDao.builder()
                .id(1L)
                .course("Test Course")
                .build();

        when(courseRepository.findAll()).thenReturn(List.of(courseDao));
        when(mapper.map(any(), eq(CourseDto.class))).thenReturn(
                CourseDto.builder()
                        .id(1L)
                        .course("Test")
                        .build()
        );

        //test service
        ResponseEntity<Object> responseEntity = courseService.getAllCourse();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<CourseDto> courseDtoList = (List<CourseDto>) apiResponse.getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(1, courseDtoList.size());

    }

    @Test
    void getAllCourseNull_Success_Test() {
        //mock
        CourseDto courseDto = new CourseDto();


        when(mapper.map(any(),eq(CourseDto.class))).thenReturn(courseDto);

        //test service
        ResponseEntity<Object> responseEntity = courseService.getAllCourse();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<CourseDto> courseDtoList = (List<CourseDto>) apiResponse.getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(0, courseDtoList.size());

    }

    @Test
    void getAllCourseError_Exception_Test() {

        //mock
        when(courseService.getAllCourse()).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = courseService.getAllCourse();

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void getCourseByIdNotNull_Success_Test() {
        //mocking
        CourseDao courseDao = CourseDao.builder()
                .id(1L)
                .build();

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(courseDao));
        when(mapper.map(any(), eq(CourseDto.class))).thenReturn(CourseDto.builder()
                .id(1L)
                .build());

        //test service
        ResponseEntity<Object> responseEntity = courseService.getCourseById(anyLong());

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void getCourseByIdNotFound_Test() {

        //test service
        ResponseEntity<Object> responseEntity = courseService.getCourseById(anyLong());

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void getCourseById_Error_Test() {

        //mocking
        when(courseService.getCourseById(anyLong())).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = courseService.getCourseById(anyLong());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void createNewCourseWithPayload_Success_Test() {
        //mocking
        CourseDao courseDao = CourseDao.builder()
                .id(1L)
                .build();

        //payload
        CourseDto courseDto = CourseDto.builder()
                .id(1L)
                .course("Test")
                .build();

        when(mapper.map(any(),eq(CourseDao.class))).thenReturn(courseDao);
        when(mapper.map(any(),eq(CourseDto.class))).thenReturn(courseDto);
        when(courseRepository.save(any())).thenReturn(courseDao);

        //test service
        ResponseEntity<Object> responseEntity = courseService.createNewCourse(
                CourseDto.builder()
                        .id(1L)
                        .course("Test")
                        .build()
        );
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        CourseDto resultData = (CourseDto) Objects.requireNonNull(apiResponse).getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(courseDto.getCourse(), resultData.getCourse());

    }

    @Test
    void createNewCourseException_Error_Test() {
        //mocking
        when(courseService.createNewCourse(mapper.map(any(),eq(CourseDto.class)))).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = courseService.createNewCourse(CourseDto.builder().build());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void updateCourse_Success_Test() {
        //mocking
        CourseDao courseDao = CourseDao.builder()
                .id(1L)
                .course("Test")
                .build();

        CourseDto courseDto = CourseDto.builder()
                .id(1L)
                .course("Update Test")
                .build();

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(courseDao));
        when(mapper.map(any(),eq(CourseDao.class))).thenReturn(courseDao);
        when(mapper.map(any(),eq(CourseDto.class))).thenReturn(courseDto);

        //test service
        ResponseEntity<Object> responseEntity = courseService.updateCourse(
                anyLong(),
                CourseDto.builder()
                        .course("Update Test")
                        .build()
        );

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        CourseDto resultData = (CourseDto) apiResponse.getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(courseDto.getCourse(),resultData.getCourse());

    }

    @Test
    void updateCourseNotFound_Failed_Test() {
        //mocking
        when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());

        //test service
        ResponseEntity<Object> responseEntity = courseService.updateCourse(
                anyLong(),
                CourseDto.builder()
                        .course("Update Test")
                        .build()
        );

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void updateCourseException_Error_Test() {
        //mocking
        when(courseRepository.save(any())).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = courseService.updateCourse(
                anyLong(),
                CourseDto.builder()
                        .course("Update Test")
                        .build()
        );

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void deleteCourse_Success_Test() {
        //mocking
        CourseDao courseDao = CourseDao.builder()
                .id(1L)
                .build();

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(courseDao));

        //test service
        ResponseEntity<Object> responseEntity = courseService.deleteCourse(anyLong());

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void deleteCourseIdNotFound_Failed_Test() {
        //mocking
        when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());

        //test service
        ResponseEntity<Object> responseEntity = courseService.deleteCourse(anyLong());

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void deleteCourseException_Error_Test() {
        //mocking
        when(courseService.deleteCourse(anyLong())).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = courseService.deleteCourse(anyLong());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
    }

}