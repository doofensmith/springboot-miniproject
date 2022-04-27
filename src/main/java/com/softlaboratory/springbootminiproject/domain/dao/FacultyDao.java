package com.softlaboratory.springbootminiproject.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "M_FACULTY")
@SQLDelete(sql = "UPDATE m_faculty SET is_deleted=TRUE, deleted_at=CURRENT_TIMESTAMP WHERE id=?")
@Where(clause = "is_deleted = FALSE")
public class FacultyDao extends BaseDaoSoftDelete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "faculty", nullable = false)
    private String faculty;


}
