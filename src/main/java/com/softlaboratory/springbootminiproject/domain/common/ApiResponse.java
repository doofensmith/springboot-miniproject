package com.softlaboratory.springbootminiproject.domain.common;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@MappedSuperclass
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApiResponse {

    private LocalDateTime timestamp;
    private String responseCode;
    private String message;
    private Object data;

}
