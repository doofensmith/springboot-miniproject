package com.softlaboratory.springbootminiproject.service;

import com.softlaboratory.springbootminiproject.domain.dao.UserDao;
import com.softlaboratory.springbootminiproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserService.class)
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void loadUserByUsername_Success_Test() {
        //mock
        UserDao userDao = UserDao.builder()
                .id(1L)
                .username("username")
                .build();

        when(userRepository.getDistinctTopByUsername(anyString())).thenReturn(userDao);

        //test
        UserDetails userDetails = userService.loadUserByUsername(anyString());

        assertEquals("username", userDetails.getUsername());
    }

//    @Test
//    void loadUserByUsername_Null_Test() {
//        //mock
//        UserDao userDao = UserDao.builder()
//                .id(1L)
//                .build();
//
//        when(userRepository.getDistinctTopByUsername(anyString())).thenThrow(NullPointerException.class);
//
//        //test service
//        UserDao userDao1 = userService.loadUserByUsername(anyString());
//
//        //test
//        assertEquals(userDetails.getUsername(),null);
//
//    }

}