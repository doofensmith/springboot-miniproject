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
public class FacultyDto implements Serializable {

    private static final long serialVersionUID = -8358432593555104654L;

    private Long id;
    private String code;
    private String faculty;

}
