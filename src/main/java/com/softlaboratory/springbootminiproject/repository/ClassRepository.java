package com.softlaboratory.springbootminiproject.repository;

import com.softlaboratory.springbootminiproject.domain.dao.ClassDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<ClassDao, Long> {
    @Query("select c from ClassDao c where c.id = ?1")
    List<ClassDao> findByIdEquals(Long id);

    @Query("select c from ClassDao c inner join c.enrollmentDaoList enrollmentDaoList " +
            "where enrollmentDaoList.studentDao.id = ?1")
    List<ClassDao> findByEnrollmentDaoList_StudentDao_IdEquals(Long id);





}
