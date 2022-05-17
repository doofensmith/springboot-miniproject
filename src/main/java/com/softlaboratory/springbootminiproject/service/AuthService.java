package com.softlaboratory.springbootminiproject.service;

import com.softlaboratory.springbootminiproject.constant.AppConstant;
import com.softlaboratory.springbootminiproject.domain.dao.UserDao;
import com.softlaboratory.springbootminiproject.domain.dto.TokenDto;
import com.softlaboratory.springbootminiproject.domain.dto.UserDto;
import com.softlaboratory.springbootminiproject.repository.UserRepository;
import com.softlaboratory.springbootminiproject.security.JwtTokenProvider;
import com.softlaboratory.springbootminiproject.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<Object> register(UserDto request) {
        try {
            UserDao userDao = UserDao.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role("STUDENT")
                    .active(true)
                    .build();
            userRepository.save(userDao);

            UserDto userDto = UserDto.builder()
                    .username(userDao.getUsername())
                    .password(userDao.getPassword())
                    .build();

            return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_SUCCESS, userDto);
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, null);
        }
    }

    public ResponseEntity<Object> login(UserDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);

            TokenDto tokenDto = TokenDto.builder()
                    .token(jwt)
                    .build();

            return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_SUCCESS, tokenDto);
        }catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, AppConstant.KEY_ERROR, e.getMessage());
        }

    }

}
