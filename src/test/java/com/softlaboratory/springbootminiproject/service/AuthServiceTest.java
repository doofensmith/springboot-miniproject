package com.softlaboratory.springbootminiproject.service;

import com.softlaboratory.springbootminiproject.domain.dao.UserDao;
import com.softlaboratory.springbootminiproject.domain.dto.TokenDto;
import com.softlaboratory.springbootminiproject.domain.dto.UserDto;
import com.softlaboratory.springbootminiproject.repository.UserRepository;
import com.softlaboratory.springbootminiproject.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AuthService.class)
class AuthServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private ModelMapper mapper;

    @Autowired
    private AuthService authService;

    @Test
    void register_Success_Test() {
        //mocking
        UserDao userDao = UserDao.builder()
                .id(1L)
                .build();

        when(userRepository.save(any())).thenReturn(userDao);

        //test
        ResponseEntity<Object> responseEntity = authService.register(
                UserDto.builder()
                        .username("tes")
                        .password("tes")
                        .build()
        );

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void register_Exception_Test() {
        //mocking
        UserDao userDao = UserDao.builder()
                .id(1L)
                .build();

        when(userRepository.save(any())).thenThrow(NullPointerException.class);

        //test
        ResponseEntity<Object> responseEntity = authService.register(
                UserDto.builder()
                        .username("tes")
                        .password("tes")
                        .build()
        );

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void login_Success_Test() {
        //mocking
        TokenDto tokenDto = TokenDto.builder()
                .token("token")
                .build();

        when(mapper.map(any(),eq(TokenDto.class))).thenReturn(tokenDto);

        //test
        ResponseEntity<Object> responseEntity = authService.login(
                UserDto.builder()
                        .username("tes")
                        .password("tes")
                        .build()
        );

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void login_Exception_Test() {
        //mocking
        when(authService.login(UserDto.builder()
                .username("tes")
                .build())).thenThrow(NullPointerException.class);

        //test
        ResponseEntity<Object> responseEntity = authService.login(
                UserDto.builder()
                        .username("tes")
                        .build()
        );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
    }

}