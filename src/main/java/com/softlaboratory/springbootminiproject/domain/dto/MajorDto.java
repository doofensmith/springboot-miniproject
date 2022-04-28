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
public class MajorDto implements Serializable {
    private static final long serialVersionUID = -3179142915494709420L;

    private Long id;
    private String code;
    private String major;

}
