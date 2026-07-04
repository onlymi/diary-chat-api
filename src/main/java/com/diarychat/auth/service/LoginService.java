package com.diarychat.auth.service;

import com.diarychat.auth.dto.LoginRequest;
import com.diarychat.auth.dto.LoginResponse;
import com.diarychat.auth.exception.InvalidLoginException;
import com.diarychat.user.entity.User;
import com.diarychat.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUserId(request.userId())
            .orElseThrow(() ->
                new InvalidLoginException("아이디 또는 비밀번호가 올바르지 않습니다.")
            );
        
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidLoginException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        
        return new LoginResponse("accessToken", "refreshToken");
    }

}
