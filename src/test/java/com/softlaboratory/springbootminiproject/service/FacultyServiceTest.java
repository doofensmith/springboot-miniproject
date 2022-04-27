package com.softlaboratory.springbootminiproject.service;

import com.softlaboratory.springbootminiproject.domain.dao.FacultyDao;
import com.softlaboratory.springbootminiproject.domain.dto.FacultyDto;
import com.softlaboratory.springbootminiproject.repository.FacultyRepository;
import com.softlaboratory.springbootminiproject.domain.dao.ApiResponse;
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
@SpringBootTest(classes = FacultyService.class)
class FacultyServiceTest {

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private ModelMapper mapper;

    @Autowired
    private FacultyService facultyService;

    @Test
    void getAllFacultyNotNull_Success_Test() {

        //mock
        FacultyDao facultyDao = FacultyDao.builder()
                .id(1L)
                .faculty("Saintek")
                .build();

        when(facultyRepository.findAll()).thenReturn(List.of(facultyDao));
        when(mapper.map(any(), eq(FacultyDto.class))).thenReturn(
                FacultyDto.builder()
                        .id(1L)
                        .faculty("Test")
                        .build()
        );

        //test service
        ResponseEntity<Object> responseEntity = facultyService.getAllFaculty();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<FacultyDto> facultyDtoList = (List<FacultyDto>) apiResponse.getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(1, facultyDtoList.size());

    }

    @Test
    void getAllFacultyNull_Success_Test() {
        //mock
        FacultyDto facultyDto = new FacultyDto();


        when(mapper.map(any(),eq(FacultyDto.class))).thenReturn(facultyDto);

        //test service
        ResponseEntity<Object> responseEntity = facultyService.getAllFaculty();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<FacultyDto> facultyDtoList = (List<FacultyDto>) apiResponse.getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(0, facultyDtoList.size());

    }

    @Test
    void getAllFacultyError_Exception_Test() {

        //mock
        when(facultyService.getAllFaculty()).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = facultyService.getAllFaculty();

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void getFacultyByIdNotNull_Success_Test() {
        //mocking
        FacultyDao facultyDao = FacultyDao.builder()
                .id(1L)
                .build();

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(facultyDao));
        when(mapper.map(any(), eq(FacultyDto.class))).thenReturn(FacultyDto.builder()
                .id(1L)
                .build());

        //test service
        ResponseEntity<Object> responseEntity = facultyService.getFacultyById(anyLong());

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void getFacultyByIdNotFound_Test() {

        //test service
        ResponseEntity<Object> responseEntity = facultyService.getFacultyById(anyLong());

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void getFacultyById_Error_Test() {

        //mocking
        when(facultyService.getFacultyById(anyLong())).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = facultyService.getFacultyById(anyLong());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void addFacultyWithPayload_Success_Test() {
        //mocking
        FacultyDao facultyDao = FacultyDao.builder()
                .id(1L)
                .build();

        //payload
        FacultyDto facultyDto = FacultyDto.builder()
                .id(1L)
                .faculty("Test")
                .build();

        when(mapper.map(any(),eq(FacultyDao.class))).thenReturn(facultyDao);
        when(mapper.map(any(),eq(FacultyDto.class))).thenReturn(facultyDto);
        when(facultyRepository.save(any())).thenReturn(facultyDao);

        //test service
        ResponseEntity<Object> responseEntity = facultyService.addNewFaculty(
                FacultyDto.builder()
                        .id(1L)
                        .faculty("Test")
                        .build()
        );
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        FacultyDto resultData = (FacultyDto) Objects.requireNonNull(apiResponse).getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(facultyDto.getFaculty(), resultData.getFaculty());

    }

    @Test
    void addFacultyException_Error_Test() {
        //mocking
        when(facultyService.addNewFaculty(mapper.map(any(),eq(FacultyDto.class)))).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = facultyService.addNewFaculty(FacultyDto.builder().build());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void updateFaculty_Success_Test() {
        //mocking
        FacultyDao facultyDao = FacultyDao.builder()
                .id(1L)
                .faculty("Test")
                .build();

        FacultyDto facultyDto = FacultyDto.builder()
                .id(1L)
                .faculty("Update Test")
                .build();

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(facultyDao));
        when(mapper.map(any(),eq(FacultyDao.class))).thenReturn(facultyDao);
        when(mapper.map(any(),eq(FacultyDto.class))).thenReturn(facultyDto);

        //test service
        ResponseEntity<Object> responseEntity = facultyService.updateFaculty(
                anyLong(),
                FacultyDto.builder()
                        .faculty("Update Test")
                        .build()
        );

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        FacultyDto resultData = (FacultyDto) apiResponse.getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(facultyDto.getFaculty(),resultData.getFaculty());

    }

    @Test
    void updateFacultyNotFound_Failed_Test() {
        //mocking
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.empty());

        //test service
        ResponseEntity<Object> responseEntity = facultyService.updateFaculty(
                anyLong(),
                FacultyDto.builder()
                        .faculty("Update Test")
                        .build()
        );

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void updateFacultyException_Error_Test() {
        //mocking
        when(facultyRepository.save(any())).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = facultyService.updateFaculty(
                anyLong(),
                FacultyDto.builder()
                        .faculty("Update Test")
                        .build()
        );

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void deleteFaculty_Success_Test() {
        //mocking
        FacultyDao facultyDao = FacultyDao.builder()
                .id(1L)
                .build();

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(facultyDao));

        //test service
        ResponseEntity<Object> responseEntity = facultyService.deleteFaculty(anyLong());

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void deleteFacultyIdNotFound_Failed_Test() {
        //mocking
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.empty());

        //test service
        ResponseEntity<Object> responseEntity = facultyService.deleteFaculty(anyLong());

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void deleteFacultyException_Error_Test() {
        //mocking
        //doThrow().when(facultyRepository).deleteById(anyLong());
        when(facultyService.deleteFaculty(anyLong())).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = facultyService.deleteFaculty(anyLong());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
    }

}