package com.diarychat.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtProvider {

    private static final String TOKEN_TYPE_CLAIM = "tokenType";
    private static final String USER_ID_CLAIM = "userId";

    private final JwtProperties properties;
    private final SecretKey signingKey;
    private final JwtParser jwtParser;

    public JwtProvider(JwtProperties properties) {
        this.properties = properties;
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.secret()));
        this.jwtParser = Jwts.parser()
                .verifyWith(signingKey)
                .build();
    }

    public String createAccessToken(Long userKey, String userId) {
        return createToken(
                userKey,
                JwtTokenType.ACCESS,
                properties.accessTokenExpiration(),
                userId
        );
    }

    public String createRefreshToken(Long userKey) {
        return createToken(
                userKey,
                JwtTokenType.REFRESH,
                properties.refreshTokenExpiration(),
                null
        );
    }

    public boolean validateToken(String token, JwtTokenType expectedType) {
        try {
            Claims claims = parseClaims(token);
            return expectedType.name().equals(claims.get(TOKEN_TYPE_CLAIM, String.class));
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    public Long getUserKey(String token) {
        return Long.valueOf(parseClaims(token).getSubject());
    }

    public String getUserId(String token) {
        return parseClaims(token).get(USER_ID_CLAIM, String.class);
    }

    private String createToken(
            Long userKey,
            JwtTokenType tokenType,
            long expirationMillis,
            String userId
    ) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plusMillis(expirationMillis);

        var builder = Jwts.builder()
                .subject(userKey.toString())
                .claim(TOKEN_TYPE_CLAIM, tokenType.name())
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiresAt));

        if (userId != null) {
            builder.claim(USER_ID_CLAIM, userId);
        }

        return builder
                .signWith(signingKey)
                .compact();
    }

    private Claims parseClaims(String token) {
        return jwtParser
                .parseSignedClaims(token)
                .getPayload();
    }
}
