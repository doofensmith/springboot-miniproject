package com.softlaboratory.springbootminiproject.repository;

import com.softlaboratory.springbootminiproject.domain.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDao, Long> {

    UserDao getDistinctTopByUsername(String username);

}
