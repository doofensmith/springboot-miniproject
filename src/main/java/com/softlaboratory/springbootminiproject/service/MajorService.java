package com.softlaboratory.springbootminiproject.service;

import com.softlaboratory.springbootminiproject.constant.AppConstant;
import com.softlaboratory.springbootminiproject.domain.dao.MajorDao;
import com.softlaboratory.springbootminiproject.domain.dto.MajorDto;
import com.softlaboratory.springbootminiproject.repository.MajorRepository;
import com.softlaboratory.springbootminiproject.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MajorService {

    @Autowired
    private MajorRepository majorRepository;

    public ResponseEntity<Object> getAllMajor() {
        try {
            List<MajorDao> majorDaoList = majorRepository.findAll();
            List<MajorDto> majorDtoList = new ArrayList<>();
            for (MajorDao majorDao : majorDaoList) {
                majorDtoList.add(MajorDto.builder()
                        .id(majorDao.getId())
                        .code(majorDao.getCode())
                        .major(majorDao.getMajor())
                        .build()
                );
            }
            return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_SUCCESS, majorDtoList);
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

    public ResponseEntity<Object> getMajorById(Long id) {
        try {
            Optional<MajorDao> majorDao = majorRepository.findById(id);
            if (majorDao.isPresent()) {
                MajorDto majorDto = MajorDto.builder()
                        .id(majorDao.get().getId())
                        .code(majorDao.get().getCode())
                        .major(majorDao.get().getMajor())
                        .build();
                return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_SUCCESS, majorDto);
            }else {
                return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
            }
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

    public ResponseEntity<Object> addMajor(MajorDto request) {
        try {
            MajorDao majorDao = MajorDao.builder()
                    .code(request.getCode())
                    .major(request.getMajor())
                    .build();
            majorRepository.save(majorDao);

            MajorDto majorDto = MajorDto.builder()
                    .id(majorDao.getId())
                    .code(majorDao.getCode())
                    .major(majorDao.getMajor())
                    .build();

            return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_CREATED, majorDto);
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

    public ResponseEntity<Object> updateMajor(Long id, MajorDto request) {
        try {
            Optional<MajorDao> majorDaoOld = majorRepository.findById(id);
            if (majorDaoOld.isPresent()) {
                MajorDao majorDaoNew = majorDaoOld.get();
                majorDaoNew.setCode(request.getCode());
                majorDaoNew.setMajor(request.getMajor());
                majorRepository.save(majorDaoNew);

                MajorDto majorDto = MajorDto.builder()
                        .id(majorDaoNew.getId())
                        .code(majorDaoNew.getCode())
                        .major(majorDaoNew.getMajor())
                        .build();

                return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_UPDATED, majorDto);
            }else {
                return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
            }
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

    public ResponseEntity<Object> deleteMajor(Long id) {
        try {
            Optional<MajorDao> majorDao = majorRepository.findById(id);
            if (majorDao.isPresent()) {
                majorRepository.deleteById(id);
                return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_DELETED, null);
            }else {
                return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
            }
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

}
