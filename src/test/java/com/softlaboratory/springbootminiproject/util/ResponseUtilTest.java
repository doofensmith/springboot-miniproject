package com.softlaboratory.springbootminiproject.util;

import com.softlaboratory.springbootminiproject.constant.AppConstant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ResponseUtil.class)
class ResponseUtilTest {

    @Test
    void buildResponse_Success_Test() {

        ResponseEntity<Object> response = ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_SUCCESS, null);

        //assertion
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());

    }

}