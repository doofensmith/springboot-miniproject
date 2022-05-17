package com.softlaboratory.springbootminiproject.repository;

import com.softlaboratory.springbootminiproject.domain.dao.FacultyDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends JpaRepository<FacultyDao, Long> {
}
