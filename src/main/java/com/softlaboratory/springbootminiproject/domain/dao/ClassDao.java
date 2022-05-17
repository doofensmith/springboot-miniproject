package com.softlaboratory.springbootminiproject.domain.dao;

import com.softlaboratory.springbootminiproject.domain.common.BaseDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "t_class")
public class ClassDao extends BaseDao implements Serializable {

    private static final long serialVersionUID = 2661149272721979276L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "max_student", nullable = false)
    private int maxStudent;

    @OneToOne
    @JoinColumn(name = "lecturer_id", nullable = false)
    private LecturerDao lecturer;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private CourseDao course;

    @OneToMany(mappedBy = "classDao")
    private List<EnrollmentDao> enrollmentDaoList;

}
