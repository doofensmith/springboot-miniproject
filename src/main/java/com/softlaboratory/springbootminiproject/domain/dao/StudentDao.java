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
@Table(name = "m_student")
@SQLDelete(sql = "update m_student set is_deleted = true, deleted_at = current_timestamp where id = ?")
@Where(clause = "is_deleted = false")
public class StudentDao extends BaseDaoSoftDelete implements Serializable {

    private static final long serialVersionUID = 7263294901762248395L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nim", nullable = false)
    private String nim;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "total_credit", nullable = false)
    private int totalCredit;

    @Column(name = "max_credit", nullable = false)
    private int maxCredit;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private FacultyDao faculty;

    @ManyToOne
    @JoinColumn(name = "major_id", nullable = false)
    private MajorDao major;

    @OneToMany(mappedBy = "studentDao")
    private List<EnrollmentDao> enrollmentDaoList;

}
