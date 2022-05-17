package com.softlaboratory.springbootminiproject.util;

import com.softlaboratory.springbootminiproject.domain.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ResponseUtil {

    private ResponseUtil() {
        //
    }

    public static ResponseEntity<Object> build(HttpStatus httpStatus, String message, Object data) {

        ApiResponse apiResponse = ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .responseCode(String.valueOf(httpStatus.value()))
                .message(message)
                .data(data)
                .build();

        return new ResponseEntity<>(apiResponse, httpStatus);

    }

}
