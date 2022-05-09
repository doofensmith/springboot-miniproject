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
public class StudentDto implements Serializable {

    private Long id;
    private String nim;
    private String name;

}
