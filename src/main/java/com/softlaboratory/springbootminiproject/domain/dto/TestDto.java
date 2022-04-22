package com.softlaboratory.springbootminiproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TestDto implements Serializable {

    private static final long serialVersionUID = -4399513852352066635L;

    private Long id;
    private String data;

}
