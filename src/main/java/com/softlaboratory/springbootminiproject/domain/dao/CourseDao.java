package com.softlaboratory.springbootminiproject.domain.dao;

import com.softlaboratory.springbootminiproject.domain.common.BaseDaoSoftDelete;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "m_course")
@SQLDelete(sql = "update m_course set is_deleted = true, deleted_at = current_timestamp where id = ?")
@Where(clause = "is_deleted = false")
public class CourseDao extends BaseDaoSoftDelete implements Serializable {

    private static final long serialVersionUID = 2279846687857556526L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "course", nullable = false)
    private String course;

    @Column(name = "credit", nullable = false)
    private int credit;

    @OneToOne(mappedBy = "course")
    private ClassDao classDaos;


}
