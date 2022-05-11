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
public class EnrollmentDto implements Serializable {

    private static final long serialVersionUID = -3293787978463410967L;

    private Long id;
    private StudentDto studentDto;
    private ClassDto classDto;

}
