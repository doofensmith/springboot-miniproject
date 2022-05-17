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
public class StudentDto implements Serializable {

    private Long id;
    private String nim;
    private String name;
    private int totalCredit;
    private int maxCredit;
    private FacultyDto faculty;
    private MajorDto major;

}
