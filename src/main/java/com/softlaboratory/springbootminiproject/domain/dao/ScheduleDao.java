package com.softlaboratory.springbootminiproject.domain.dao;

import com.softlaboratory.springbootminiproject.domain.common.BaseDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "t_schedule")
public class ScheduleDao extends BaseDao implements Serializable {

    private static final long serialVersionUID = 1695176478338067452L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day", nullable = false)
    private String day;

    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Column(name = "end_time",nullable = false)
    private Date endTime;

    @OneToOne
    @JoinColumn(name = "class_id", nullable = false)
    private ClassDao classDao;

}
