package com.softlaboratory.springbootminiproject.service;

import com.softlaboratory.springbootminiproject.domain.common.ApiResponse;
import com.softlaboratory.springbootminiproject.domain.dao.MajorDao;
import com.softlaboratory.springbootminiproject.domain.dto.MajorDto;
import com.softlaboratory.springbootminiproject.repository.MajorRepository;
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
@SpringBootTest(classes = MajorService.class)
class MajorServiceTest {

    @MockBean
    private MajorRepository majorRepository;

    @MockBean
    private ModelMapper mapper;

    @Autowired
    private MajorService majorService;

    @Test
    void getAllMajorNotNull_Success_Test() {

        //mock
        MajorDao majorDao = MajorDao.builder()
                .id(1L)
                .major("Informatika")
                .build();

        when(majorRepository.findAll()).thenReturn(List.of(majorDao));
        when(mapper.map(any(), eq(MajorDto.class))).thenReturn(
                MajorDto.builder()
                        .id(1L)
                        .major("Test")
                        .build()
        );

        //test service
        ResponseEntity<Object> responseEntity = majorService.getAllMajor();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<MajorDto> majorDtoList = (List<MajorDto>) apiResponse.getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(1, majorDtoList.size());

    }

    @Test
    void getAllMajorNull_Success_Test() {
        //mock
        MajorDto majorDto = new MajorDto();


        when(mapper.map(any(),eq(MajorDto.class))).thenReturn(majorDto);

        //test service
        ResponseEntity<Object> responseEntity = majorService.getAllMajor();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<MajorDto> majorDtoList = (List<MajorDto>) apiResponse.getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(0, majorDtoList.size());

    }

    @Test
    void getAllMajorError_Exception_Test() {

        //mock
        when(majorService.getAllMajor()).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = majorService.getAllMajor();

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void getMajorByIdNotNull_Success_Test() {
        //mocking
        MajorDao majorDao = MajorDao.builder()
                .id(1L)
                .build();

        when(majorRepository.findById(anyLong())).thenReturn(Optional.of(majorDao));
        when(mapper.map(any(), eq(MajorDto.class))).thenReturn(MajorDto.builder()
                .id(1L)
                .build());

        //test service
        ResponseEntity<Object> responseEntity = majorService.getMajorById(anyLong());

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void getMajorByIdNotFound_Test() {

        //test service
        ResponseEntity<Object> responseEntity = majorService.getMajorById(anyLong());

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void getMajorById_Error_Test() {

        //mocking
        when(majorService.getMajorById(anyLong())).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = majorService.getMajorById(anyLong());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void addMajorWithPayload_Success_Test() {
        //mocking
        MajorDao majorDao = MajorDao.builder()
                .id(1L)
                .build();

        //payload
        MajorDto majorDto = MajorDto.builder()
                .id(1L)
                .major("Test")
                .build();

        when(mapper.map(any(),eq(MajorDao.class))).thenReturn(majorDao);
        when(mapper.map(any(),eq(MajorDto.class))).thenReturn(majorDto);
        when(majorRepository.save(any())).thenReturn(majorDao);

        //test service
        ResponseEntity<Object> responseEntity = majorService.addMajor(
                majorDto.builder()
                        .id(1L)
                        .major("Test")
                        .build()
        );
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        MajorDto resultData = (MajorDto) Objects.requireNonNull(apiResponse).getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(majorDto.getMajor(), resultData.getMajor());

    }

    @Test
    void addMajorException_Error_Test() {
        //mocking
        when(majorService.addMajor(mapper.map(any(),eq(MajorDto.class)))).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = majorService.addMajor(MajorDto.builder().build());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void updateMajor_Success_Test() {
        //mocking
        MajorDao majorDao = MajorDao.builder()
                .id(1L)
                .major("Test")
                .build();

        MajorDto majorDto = MajorDto.builder()
                .id(1L)
                .major("Update Test")
                .build();

        when(majorRepository.findById(anyLong())).thenReturn(Optional.of(majorDao));
        when(mapper.map(any(),eq(MajorDao.class))).thenReturn(majorDao);
        when(mapper.map(any(),eq(MajorDto.class))).thenReturn(majorDto);

        //test service
        ResponseEntity<Object> responseEntity = majorService.updateMajor(
                anyLong(),
                MajorDto.builder()
                        .major("Update Test")
                        .build()
        );

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        MajorDto resultData = (MajorDto) apiResponse.getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(majorDto.getMajor(),resultData.getMajor());

    }

    @Test
    void updateMajorNotFound_Failed_Test() {
        //mocking
        when(majorRepository.findById(anyLong())).thenReturn(Optional.empty());

        //test service
        ResponseEntity<Object> responseEntity = majorService.updateMajor(
                anyLong(),
                MajorDto.builder()
                        .major("Update Test")
                        .build()
        );

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void updateMajorException_Error_Test() {
        //mocking
        when(majorRepository.save(any())).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = majorService.updateMajor(
                anyLong(),
                MajorDto.builder()
                        .major("Update Test")
                        .build()
        );

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void deleteMajor_Success_Test() {
        //mocking
        MajorDao majorDao = MajorDao.builder()
                .id(1L)
                .build();

        when(majorRepository.findById(anyLong())).thenReturn(Optional.of(majorDao));

        //test service
        ResponseEntity<Object> responseEntity = majorService.deleteMajor(anyLong());

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void deleteMajorIdNotFound_Failed_Test() {
        //mocking
        when(majorRepository.findById(anyLong())).thenReturn(Optional.empty());

        //test service
        ResponseEntity<Object> responseEntity = majorService.deleteMajor(anyLong());

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void deleteMajorException_Error_Test() {
        //mocking
        //doThrow().when(majorRepository).deleteById(anyLong());
        when(majorService.deleteMajor(anyLong())).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = majorService.deleteMajor(anyLong());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
    }

}