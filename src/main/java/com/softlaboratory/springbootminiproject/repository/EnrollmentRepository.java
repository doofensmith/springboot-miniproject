package com.softlaboratory.springbootminiproject.repository;

import com.softlaboratory.springbootminiproject.domain.dao.EnrollmentDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentDao, Long> {

    @Query("select e from EnrollmentDao e where e.studentDao.id = ?1")
    List<EnrollmentDao> findByStudentDao_IdEquals(Long id);

    @Query("select e from EnrollmentDao e where e.studentDao.nim = ?1")
    List<EnrollmentDao> findByStudentDao_NimEquals(String nim);



}
