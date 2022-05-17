package com.softlaboratory.springbootminiproject.service;

import com.softlaboratory.springbootminiproject.constant.AppConstant;
import com.softlaboratory.springbootminiproject.domain.dao.FacultyDao;
import com.softlaboratory.springbootminiproject.domain.dto.FacultyDto;
import com.softlaboratory.springbootminiproject.repository.FacultyRepository;
import com.softlaboratory.springbootminiproject.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;


    public ResponseEntity<Object> getAllFaculty() {

        try {
            List<FacultyDao> facultyDaoList = facultyRepository.findAll();
            List<FacultyDto> facultyDtoList = new ArrayList<>();
            for (FacultyDao facultyDao : facultyDaoList) {
                facultyDtoList.add(FacultyDto.builder()
                        .id(facultyDao.getId())
                        .code(facultyDao.getCode())
                        .faculty(facultyDao.getFaculty())
                        .build()
                );
            }

            return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_SUCCESS, facultyDtoList);
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }

    }

    public ResponseEntity<Object> getFacultyById(Long id) {

        try {
            Optional<FacultyDao> facultyDao = facultyRepository.findById(id);

            if (facultyDao.isPresent()) {
                FacultyDto facultyDto = FacultyDto.builder()
                        .id(facultyDao.get().getId())
                        .code(facultyDao.get().getCode())
                        .faculty(facultyDao.get().getFaculty())
                        .build();

                return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_SUCCESS, facultyDto);

            }else {
                return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
            }
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }

    }

    public ResponseEntity<Object> addNewFaculty(FacultyDto payload) {

        try {
            FacultyDao facultyDao = FacultyDao.builder()
                    .code(payload.getCode())
                    .faculty(payload.getFaculty())
                    .build();
            facultyRepository.save(facultyDao);

            FacultyDto facultyDto = FacultyDto.builder()
                    .id(facultyDao.getId())
                    .code(facultyDao.getCode())
                    .faculty(facultyDao.getFaculty())
                    .build();

            return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_CREATED, facultyDto);
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }

    }

    public ResponseEntity<Object> updateFaculty(Long id, FacultyDto payload) {

        try {
            Optional<FacultyDao> facultyDaoOld = facultyRepository.findById(id);

            if (facultyDaoOld.isPresent()) {
                FacultyDao facultyDaoNew = facultyDaoOld.get();
                facultyDaoNew.setCode(payload.getCode());
                facultyDaoNew.setFaculty(payload.getFaculty());
                facultyRepository.save(facultyDaoNew);

                FacultyDto facultyDto = FacultyDto.builder()
                        .id(facultyDaoNew.getId())
                        .code(facultyDaoNew.getCode())
                        .faculty(facultyDaoNew.getFaculty())
                        .build();

                return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_UPDATED, facultyDto);

            }else {
                return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
            }
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }

    }

    public ResponseEntity<Object> deleteFaculty(Long id) {

        try {
            Optional<FacultyDao> facultyDao = facultyRepository.findById(id);

            if (facultyDao.isPresent()) {
                facultyRepository.deleteById(id);
                return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_DELETED, null);
            }else {
                return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
            }
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }

    }

}
