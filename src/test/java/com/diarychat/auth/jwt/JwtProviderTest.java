package com.diarychat.auth.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtProviderTest {

    private JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        JwtProperties properties = new JwtProperties(
                "MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=",
                1_800_000,
                1_209_600_000
        );
        jwtProvider = new JwtProvider(properties);
    }

    @Test
    void createsAndValidatesAccessToken() {
        String token = jwtProvider.createAccessToken(1L, "seungmin");

        assertThat(jwtProvider.validateToken(token, JwtTokenType.ACCESS)).isTrue();
        assertThat(jwtProvider.getUserKey(token)).isEqualTo(1L);
        assertThat(jwtProvider.getUserId(token)).isEqualTo("seungmin");
    }

    @Test
    void createsAndValidatesRefreshToken() {
        String token = jwtProvider.createRefreshToken(1L);

        assertThat(jwtProvider.validateToken(token, JwtTokenType.REFRESH)).isTrue();
        assertThat(jwtProvider.validateToken(token, JwtTokenType.ACCESS)).isFalse();
        assertThat(jwtProvider.getUserKey(token)).isEqualTo(1L);
    }

    @Test
    void rejectsTamperedToken() {
        String token = jwtProvider.createAccessToken(1L, "seungmin");

        assertThat(jwtProvider.validateToken(token + "tampered", JwtTokenType.ACCESS)).isFalse();
    }
}
