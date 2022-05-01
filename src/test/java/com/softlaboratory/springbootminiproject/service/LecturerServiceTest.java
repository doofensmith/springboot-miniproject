package com.softlaboratory.springbootminiproject.service;

import com.softlaboratory.springbootminiproject.domain.common.ApiResponse;
import com.softlaboratory.springbootminiproject.domain.dao.FacultyDao;
import com.softlaboratory.springbootminiproject.domain.dao.LecturerDao;
import com.softlaboratory.springbootminiproject.domain.dto.FacultyDto;
import com.softlaboratory.springbootminiproject.domain.dto.LecturerDto;
import com.softlaboratory.springbootminiproject.repository.FacultyRepository;
import com.softlaboratory.springbootminiproject.repository.LecturerRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LecturerService.class)
class LecturerServiceTest {

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private LecturerRepository lecturerRepository;

    @MockBean
    private ModelMapper mapper;

    @Autowired
    private LecturerService lecturerService;

    @Test
    void getAllLecturerNotNull_Success_Test() {

        //mock
        FacultyDao facultyDao = FacultyDao.builder()
                .id(1L)
                .build();

        LecturerDao lecturerDao = LecturerDao.builder()
                .id(1L)
                .name("Dosen")
                .faculty(facultyDao)
                .build();

        when(lecturerRepository.findAll()).thenReturn(List.of(lecturerDao));
        when(mapper.map(any(), eq(LecturerDto.class))).thenReturn(
                LecturerDto.builder()
                        .id(1L)
                        .name("Test")
                        .build()
        );

        //test service
        ResponseEntity<Object> responseEntity = lecturerService.getAllLecturer();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<LecturerDto> lecturerDtoList = (List<LecturerDto>) apiResponse.getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(1, lecturerDtoList.size());

    }

    @Test
    void getAllLecturerNull_Success_Test() {
        //mock
        LecturerDto lecturerDto = new LecturerDto();


        when(mapper.map(any(),eq(LecturerDto.class))).thenReturn(lecturerDto);

        //test service
        ResponseEntity<Object> responseEntity = lecturerService.getAllLecturer();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<LecturerDto> lecturerDtoList = (List<LecturerDto>) apiResponse.getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(0, lecturerDtoList.size());

    }

    @Test
    void getAllLecturerError_Exception_Test() {

        //mock
        when(lecturerRepository.findAll()).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = lecturerService.getAllLecturer();

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void getLecturerByIdNotNull_Success_Test() {
        //mocking
        FacultyDao facultyDao = FacultyDao.builder()
                .id(1L)
                .build();

        LecturerDao lecturerDao = LecturerDao.builder()
                .id(1L)
                .faculty(facultyDao)
                .build();

        when(lecturerRepository.findById(anyLong())).thenReturn(Optional.of(lecturerDao));
        when(mapper.map(any(), eq(LecturerDto.class))).thenReturn(LecturerDto.builder()
                .id(1L)
                .build());

        //test service
        ResponseEntity<Object> responseEntity = lecturerService.getLecturerById(anyLong());

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void getLecturerByIdNotFound_Test() {
        //mock
        when(lecturerRepository.findById(anyLong())).thenReturn(Optional.empty());

        //test service
        ResponseEntity<Object> responseEntity = lecturerService.getLecturerById(anyLong());

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void getLecturerById_Error_Test() {

        //mocking
        when(lecturerRepository.findById(anyLong())).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = lecturerService.getLecturerById(anyLong());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void addNewLecturer_FacultyFound_Success_Test() {
        //mocking
        FacultyDao facultyDao = FacultyDao.builder()
                .id(1L)
                .build();

        LecturerDao lecturerDao = LecturerDao.builder()
                .id(1L)
                .faculty(facultyDao)
                .build();

        LecturerDto lecturerDto = LecturerDto.builder()
                .id(1L)
                .name("Test")
                .build();


        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(facultyDao));
        when(mapper.map(any(),eq(LecturerDao.class))).thenReturn(lecturerDao);
        when(mapper.map(any(),eq(LecturerDto.class))).thenReturn(lecturerDto);
        when(lecturerRepository.save(any())).thenReturn(lecturerDao);

        //test service
        ResponseEntity<Object> responseEntity = lecturerService.addNewLecturer(
                LecturerDto.builder()
                        .id(1L)
                        .name("Test")
                        .faculty(FacultyDto.builder().id(1L).build())
                        .build()
        );
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        LecturerDto resultData = (LecturerDto) Objects.requireNonNull(apiResponse).getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(lecturerDto.getName(), resultData.getName());

    }

    @Test
    void addNewLecturer_FacultyNotFound_Failed_Test() {
        //mocking
        LecturerDao lecturerDao = LecturerDao.builder()
                .id(1L)
                .build();

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(lecturerRepository.save(any())).thenReturn(lecturerDao);

        //test service
        ResponseEntity<Object> responseEntity = lecturerService.addNewLecturer(
                LecturerDto.builder()
                        .name("Test")
                        .faculty(FacultyDto.builder().id(1L).build())
                        .build()
        );

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void addNewLecturerException_Error_Test() {
        //mocking
        when(lecturerRepository.save(any())).thenThrow(NullPointerException.class);

        //test service
        ResponseEntity<Object> responseEntity = lecturerService.addNewLecturer(LecturerDto.builder().build());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void updateLecturer_FacultyFound_Success_Test() {
        //mocking
        FacultyDao facultyDao = FacultyDao.builder()
                .id(1L)
                .build();

        LecturerDao lecturerDao = LecturerDao.builder()
                .id(1L)
                .name("Test")
                .build();

        LecturerDto lecturerDto = LecturerDto.builder()
                .id(1L)
                .name("Update Test")
                .build();

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(facultyDao));
        when(lecturerRepository.findById(anyLong())).thenReturn(Optional.of(lecturerDao));
        when(mapper.map(any(),eq(LecturerDao.class))).thenReturn(lecturerDao);
        when(mapper.map(any(),eq(LecturerDto.class))).thenReturn(lecturerDto);

        //test service
        ResponseEntity<Object> responseEntity = lecturerService.updateLecturer(
                anyLong(),
                LecturerDto.builder()
                        .name("Update Test")
                        .faculty(FacultyDto.builder().id(1L).build())
                        .build()
        );

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        LecturerDto resultData = (LecturerDto) apiResponse.getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(lecturerDto.getName(),resultData.getName());

    }

    @Test
    void updateLecturer_FacultyNotFound_Failed_Test() {
        //mocking
        LecturerDao lecturerDao = LecturerDao.builder()
                .id(1L)
                .build();

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(lecturerRepository.findById(anyLong())).thenReturn(Optional.of(lecturerDao));

        //test service
        ResponseEntity<Object> responseEntity = lecturerService.updateLecturer(
                anyLong(),
                LecturerDto.builder()
                        .name("Update")
                        .faculty(FacultyDto.builder().id(1L).build())
                        .build()
        );

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void updateLecturerNotFound_Failed_Test() {
        //mocking
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.empty());

        //test service
        ResponseEntity<Object> responseEntity = lecturerService.updateLecturer(
                anyLong(),
                LecturerDto.builder()
                        .name("Update Test")
                        .build()
        );

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void updateLecturerException_Error_Test() {
        //mocking
        when(facultyRepository.save(any())).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = lecturerService.updateLecturer(
                anyLong(),
                LecturerDto.builder()
                        .name("Update Test")
                        .build()
        );

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void deleteLecturer_Success_Test() {
        //mocking
        LecturerDao lecturerDao = LecturerDao.builder()
                .id(1L)
                .build();

        when(lecturerRepository.findById(anyLong())).thenReturn(Optional.of(lecturerDao));

        //test service
        ResponseEntity<Object> responseEntity = lecturerService.deleteLecturer(anyLong());

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void deleteLecturerIdNotFound_Failed_Test() {
        //mocking
        when(lecturerRepository.findById(anyLong())).thenReturn(Optional.empty());

        //test service
        ResponseEntity<Object> responseEntity = lecturerService.deleteLecturer(anyLong());

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void deleteLecturerException_Error_Test() {
        //mocking
        //doThrow().when(lecturerRepository).deleteById(anyLong());
        when(lecturerService.deleteLecturer(anyLong())).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = lecturerService.deleteLecturer(anyLong());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
    }

}