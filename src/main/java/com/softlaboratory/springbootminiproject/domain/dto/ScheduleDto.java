package com.softlaboratory.springbootminiproject.domain.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ScheduleDto implements Serializable {
    private static final long serialVersionUID = -249433941156072978L;

    private Long id;
    private String day;
    private Date startTime;
    private Date endTime;
    private ClassDto classDto;

}
