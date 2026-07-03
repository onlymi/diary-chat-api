package com.diarychat.user.service;

import com.diarychat.user.dto.SignupRequest;
import com.diarychat.user.dto.SignupResponse;
import com.diarychat.user.entity.User;
import com.diarychat.user.exception.DuplicateUserException;
import com.diarychat.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SignupServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private SignupService signupService;

    @BeforeEach
    void setUp() {
        signupService = new SignupService(userRepository, passwordEncoder);
    }

    @Test
    void signupEncodesPasswordAndSavesUser() {
        SignupRequest request = request();
        given(passwordEncoder.encode("12345678")).willReturn("encoded-password");
        given(userRepository.save(any(User.class))).willAnswer(invocation -> invocation.getArgument(0));

        SignupResponse response = signupService.signup(request);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertThat(captor.getValue().getPassword()).isEqualTo("encoded-password");
        assertThat(response.userId()).isEqualTo("seungmin");
        assertThat(response.email()).isEqualTo("test@example.com");
    }

    @Test
    void signupRejectsDuplicateUserId() {
        SignupRequest request = request();
        given(userRepository.existsByUserId("seungmin")).willReturn(true);

        assertThatThrownBy(() -> signupService.signup(request))
                .isInstanceOf(DuplicateUserException.class)
                .hasMessage("이미 사용 중인 userId입니다.");

        verify(userRepository, never()).save(any());
    }

    @Test
    void signupRejectsDuplicateEmail() {
        SignupRequest request = request();
        given(userRepository.existsByEmail("test@example.com")).willReturn(true);

        assertThatThrownBy(() -> signupService.signup(request))
                .isInstanceOf(DuplicateUserException.class)
                .hasMessage("이미 사용 중인 email입니다.");

        verify(userRepository, never()).save(any());
    }

    private SignupRequest request() {
        return new SignupRequest(
                "seungmin",
                "test@example.com",
                "12345678",
                "승민",
                "010-1234-5678"
        );
    }
}
