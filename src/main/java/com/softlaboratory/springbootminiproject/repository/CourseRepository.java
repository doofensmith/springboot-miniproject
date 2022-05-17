package com.softlaboratory.springbootminiproject.repository;

import com.softlaboratory.springbootminiproject.domain.dao.CourseDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseDao, Long> {
}
