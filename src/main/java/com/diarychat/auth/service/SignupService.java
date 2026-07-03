package com.diarychat.auth.service;

import com.diarychat.auth.dto.SignupRequest;
import com.diarychat.auth.dto.SignupResponse;
import com.diarychat.auth.exception.DuplicateUserException;
import com.diarychat.user.entity.User;
import com.diarychat.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        validateDuplicate(request);

        User user = User.create(
                request.userId(),
                request.email(),
                passwordEncoder.encode(request.password()),
                request.nickname(),
                request.phoneNumber()
        );

        return SignupResponse.from(userRepository.save(user));
    }

    private void validateDuplicate(SignupRequest request) {
        if (userRepository.existsByUserId(request.userId())) {
            throw new DuplicateUserException("userId");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateUserException("email");
        }
    }
}
