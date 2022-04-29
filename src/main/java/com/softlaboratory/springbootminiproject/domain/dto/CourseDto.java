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
public class CourseDto implements Serializable {

    private static final long serialVersionUID = -4337999309183212713L;

    private Long id;
    private String code;
    private String course;
    private int credit;

}
