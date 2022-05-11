package com.softlaboratory.springbootminiproject.domain.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClassDto implements Serializable {
    private static final long serialVersionUID = -727009854316542025L;

    private Long id;
    private String code;
    private int maxStudent;
    private LecturerDto lecturer;
    private CourseDto course;

}
