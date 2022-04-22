package com.softlaboratory.springbootminiproject.service;

import com.softlaboratory.springbootminiproject.constant.AppConstant;
import com.softlaboratory.springbootminiproject.domain.dao.TestDao;
import com.softlaboratory.springbootminiproject.domain.dto.TestDto;
import com.softlaboratory.springbootminiproject.repository.TestRepository;
import com.softlaboratory.springbootminiproject.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public ResponseEntity<Object> getAllTestData() {
        try {
            List<TestDao> testDaoList = testRepository.findAll();
            List<TestDto> testDtoList = new ArrayList<>();
            for (TestDao testDao : testDaoList) {
                testDtoList.add(TestDto.builder()
                        .id(testDao.getId())
                        .data(testDao.getData())
                        .build()
                );
            }
            return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_SUCCESS, testDtoList);
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

}
