package com.softlaboratory.springbootminiproject.repository;

import com.softlaboratory.springbootminiproject.domain.dao.LecturerDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LecturerRepository extends JpaRepository<LecturerDao, Long> {
}
