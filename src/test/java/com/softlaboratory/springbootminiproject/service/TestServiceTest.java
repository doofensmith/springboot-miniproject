package com.softlaboratory.springbootminiproject.service;

import com.softlaboratory.springbootminiproject.domain.dao.TestDao;
import com.softlaboratory.springbootminiproject.domain.dto.TestDto;
import com.softlaboratory.springbootminiproject.repository.TestRepository;
import com.softlaboratory.springbootminiproject.util.ApiResponse;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestService.class)
class TestServiceTest {

    @MockBean
    private TestRepository testRepository;

    @MockBean
    private ModelMapper mapper;

    @Autowired
    private TestService testService;

    @Test
    void getAllTestNotNull_Success_Test() {

        //mock
        TestDao testDao = TestDao.builder()
                .id(1L)
                .data("Test")
                .build();

        when(testRepository.findAll()).thenReturn(List.of(testDao));
        when(mapper.map(any(), eq(TestDto.class))).thenReturn(
                TestDto.builder()
                        .id(1L)
                        .data("Test")
                        .build()
        );

        //test service
        ResponseEntity<Object> responseEntity = testService.getAllTestData();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<TestDto> testDtoList = (List<TestDto>) apiResponse.getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(1, testDtoList.size());

    }

    @Test
    void getAllTestNull_Success_Test() {
        //mock
        TestDto testDto = new TestDto();

        when(mapper.map(any(),eq(TestDto.class))).thenReturn(testDto);

        //test service
        ResponseEntity<Object> responseEntity = testService.getAllTestData();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<TestDto> testDtoList = (List<TestDto>) apiResponse.getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(0, testDtoList.size());

    }

    @Test
    void getAllTestError_Exception_Test() {

        //mock
        when(testService.getAllTestData()).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = testService.getAllTestData();

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }
}