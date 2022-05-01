package com.softlaboratory.springbootminiproject.service;

import com.softlaboratory.springbootminiproject.constant.AppConstant;
import com.softlaboratory.springbootminiproject.domain.dao.FacultyDao;
import com.softlaboratory.springbootminiproject.domain.dao.LecturerDao;
import com.softlaboratory.springbootminiproject.domain.dto.FacultyDto;
import com.softlaboratory.springbootminiproject.domain.dto.LecturerDto;
import com.softlaboratory.springbootminiproject.repository.FacultyRepository;
import com.softlaboratory.springbootminiproject.repository.LecturerRepository;
import com.softlaboratory.springbootminiproject.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LecturerService {

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    public ResponseEntity<Object> getAllLecturer() {
        try {
            List<LecturerDao> lecturerDaoList = lecturerRepository.findAll();
            List<LecturerDto> lecturerDtoList = new ArrayList<>();
            for (LecturerDao lecturerDao : lecturerDaoList) {
                lecturerDtoList.add(
                        LecturerDto.builder()
                                .id(lecturerDao.getId())
                                .nidn(lecturerDao.getNidn())
                                .name(lecturerDao.getName())
                                .faculty(
                                        FacultyDto.builder()
                                                .id(lecturerDao.getFaculty().getId())
                                                .code(lecturerDao.getFaculty().getCode())
                                                .faculty(lecturerDao.getFaculty().getFaculty())
                                                .build()
                                )
                                .build()
                );
            }
            return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_SUCCESS, lecturerDtoList);
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

    public ResponseEntity<Object> getLecturerById(Long id) {
        try {
            Optional<LecturerDao> lecturerDao = lecturerRepository.findById(id);
            if (lecturerDao.isPresent()) {
                LecturerDto lecturerDto = LecturerDto.builder()
                        .id(lecturerDao.get().getId())
                        .nidn(lecturerDao.get().getNidn())
                        .name(lecturerDao.get().getName())
                        .faculty(FacultyDto.builder()
                                .id(lecturerDao.get().getFaculty().getId())
                                .code(lecturerDao.get().getFaculty().getCode())
                                .faculty(lecturerDao.get().getFaculty().getFaculty())
                                .build()
                        )
                        .build();
                return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_SUCCESS, lecturerDto);
            }else {
                return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
            }
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

    public ResponseEntity<Object> addNewLecturer(LecturerDto request) {
        try {
            Optional<FacultyDao> facultyDao = facultyRepository.findById(request.getFaculty().getId());
            if (facultyDao.isPresent()) {
                LecturerDao lecturerDao = LecturerDao.builder()
                        .nidn(request.getNidn())
                        .name(request.getName())
                        .faculty(FacultyDao.builder()
                                .id(request.getFaculty().getId())
                                .build()
                        )
                        .build();
                lecturerRepository.save(lecturerDao);

                LecturerDto lecturerDto = LecturerDto.builder()
                        .id(lecturerDao.getId())
                        .nidn(lecturerDao.getNidn())
                        .name(lecturerDao.getName())
                        .faculty(FacultyDto.builder()
                                .faculty(lecturerDao.getFaculty().getFaculty())
                                .build())
                        .build();

                return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_CREATED, lecturerDto);
            }else {
                return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
            }

        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

    public ResponseEntity<Object> updateLecturer(Long id, LecturerDto request) {
        try {
            Optional<LecturerDao> lecturerDaoOld = lecturerRepository.findById(id);
            if (lecturerDaoOld.isPresent()) {
                Optional<FacultyDao> facultyDao = facultyRepository.findById(request.getFaculty().getId());

                if (facultyDao.isPresent()) {
                    LecturerDao lecturerDaoNew = lecturerDaoOld.get();
                    lecturerDaoNew.setNidn(request.getNidn());
                    lecturerDaoNew.setName(request.getName());
                    lecturerDaoNew.setFaculty(FacultyDao.builder()
                            .id(request.getFaculty().getId())
                            .build());
                    lecturerRepository.save(lecturerDaoNew);

                    LecturerDto lecturerDto = LecturerDto.builder()
                            .id(lecturerDaoNew.getId())
                            .nidn(lecturerDaoNew.getNidn())
                            .name(lecturerDaoNew.getName())
                            .faculty(FacultyDto.builder()
                                    .faculty(lecturerDaoNew.getFaculty().getFaculty())
                                    .build())
                            .build();

                    return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_UPDATED, lecturerDto);
                }else {
                    return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
                }

            }else {
                return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
            }
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

    public ResponseEntity<Object> deleteLecturer(Long id) {
        try {
            Optional<LecturerDao> lecturerDao = lecturerRepository.findById(id);
            if (lecturerDao.isPresent()) {
                lecturerRepository.deleteById(id);
                return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_DELETED, null);
            }else {
                return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
            }
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

}
