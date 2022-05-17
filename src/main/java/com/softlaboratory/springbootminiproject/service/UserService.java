package com.softlaboratory.springbootminiproject.service;

import com.softlaboratory.springbootminiproject.domain.dao.UserDao;
import com.softlaboratory.springbootminiproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDao userDao = userRepository.getDistinctTopByUsername(username);
        if (userDao == null) {
            return null;
        }else {
            return userDao;
        }
    }

}
