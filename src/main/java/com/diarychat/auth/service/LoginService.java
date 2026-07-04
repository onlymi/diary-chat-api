package com.diarychat.auth.service;

import com.diarychat.auth.dto.LoginRequest;
import com.diarychat.auth.dto.LoginResponse;
import com.diarychat.auth.exception.InvalidLoginException;
import com.diarychat.auth.jwt.JwtProvider;
import com.diarychat.user.entity.User;
import com.diarychat.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    
    @Autowired
    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }
    
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByLoginId(request.loginId())
            .orElseThrow(() ->
                new InvalidLoginException("아이디 또는 비밀번호가 올바르지 않습니다.")
            );
        
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidLoginException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        
        String accessToken = jwtProvider.createAccessToken(user.getId());
        String refreshToken = jwtProvider.createRefreshToken(user.getId());
        
        return new LoginResponse(accessToken, refreshToken);
    }

}
