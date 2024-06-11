package com.example.demo.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class JwtTokenService implements Serializable {

    private static final String KEY_USER_ID = "userId";
    private static final String KEY_FINGERPRINT = "fingerprint";
    private static final String KEY_TOKEN_TYPE = "token_type";
    private static final String TOKEN_TYPE_ACCESS = "access";
    private static final String TOKEN_TYPE_REFRESH = "refresh";

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.token_validity_in_hours}")
    private int tokenValidityInHours;

    @Value("${jwt.refresh_token_validity_in_hours}")
    private int refreshTokenValidityInHours;

    private Algorithm algorithm = null;

    public String generateToken(Long userId, String fingerprint) {
        Instant expiredAt = ZonedDateTime.now()
            .plusHours(tokenValidityInHours)
            .toInstant();

        return JWT.create()
            .withClaim(KEY_USER_ID, userId)
            .withClaim(KEY_FINGERPRINT, fingerprint)
            .withClaim(KEY_TOKEN_TYPE, TOKEN_TYPE_ACCESS)
            .withExpiresAt(expiredAt)
            .sign(getAlgorithm());
    }

    public String generateRefreshToken(Long userId, String fingerprint) {
        Instant expiredAt = ZonedDateTime.now()
            .plusHours(refreshTokenValidityInHours)
            .toInstant();

        return JWT.create()
            .withClaim(KEY_USER_ID, userId)
            .withClaim(KEY_FINGERPRINT, fingerprint)
            .withClaim(KEY_TOKEN_TYPE, TOKEN_TYPE_REFRESH)
            .withExpiresAt(expiredAt)
            .sign(getAlgorithm());
    }

    public Optional<TokenInfo> getTokenInfo(String token) {
        try {
            DecodedJWT decode = JWT.decode(token);

            TokenInfo tokenInfo = TokenInfo.builder()
                .userId(decode.getClaim(KEY_USER_ID).asLong())
                .fingerprint(decode.getClaim(KEY_FINGERPRINT).asString())
                .tokenType(decode.getClaim(KEY_TOKEN_TYPE).asString())
                .expiresAt(decode.getExpiresAt())
                .build();

            return Optional.of(tokenInfo);
        } catch (Throwable e) {
            return Optional.empty();
        }
    }

    private Algorithm getAlgorithm() {
        if (algorithm == null) {
            algorithm = Algorithm.HMAC256(jwtSecret);
        }

        return algorithm;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class TokenInfo {
        private Long userId;
        private String fingerprint;
        private String tokenType;
        private Date expiresAt;

        public boolean isValidAsAnAccess(String userFingerprint) {
            boolean isValidType = TOKEN_TYPE_ACCESS.equals(tokenType);
            boolean isValidFingerprint = Objects.equals(userFingerprint, fingerprint);
            boolean isExpired = isTokenExpired(expiresAt);

            return isValidType && isValidFingerprint && !isExpired;
        }

        public boolean isValidAsARefresh(String userFingerprint) {
            boolean isValidType = TOKEN_TYPE_REFRESH.equals(tokenType);
            boolean isValidFingerprint = Objects.equals(userFingerprint, fingerprint);
            boolean isNotExpired = !isTokenExpired(expiresAt);

            return isValidType && isValidFingerprint && isNotExpired;
        }

        private Boolean isTokenExpired(Date expiresAt) {
            return expiresAt.before(new Date());
        }
    }
}