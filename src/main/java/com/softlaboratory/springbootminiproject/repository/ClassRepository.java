package com.softlaboratory.springbootminiproject.repository;

import com.softlaboratory.springbootminiproject.domain.dao.ClassDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<ClassDao, Long> {
}
