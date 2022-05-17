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
public class LecturerDto implements Serializable {

    private static final long serialVersionUID = -8661040073664090918L;

    private Long id;
    private String nidn;
    private String name;
    private FacultyDto faculty;

}
