package com.softlaboratory.springbootminiproject.domain.dao;

import com.softlaboratory.springbootminiproject.domain.common.BaseDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_enrollment")
@SuperBuilder
public class EnrollmentDao extends BaseDao implements Serializable {
    private static final long serialVersionUID = -3687090368556445479L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentDao studentDao;

    @Id
    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private ClassDao classDao;

}
