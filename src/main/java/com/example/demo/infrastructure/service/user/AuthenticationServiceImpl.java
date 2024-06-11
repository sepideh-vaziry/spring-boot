package com.example.demo.infrastructure.service.user;

import com.example.demo.domain.error.ErrorEnum;
import com.example.demo.infrastructure.persistence.entity.user.UserLockEntity;
import com.example.demo.infrastructure.persistence.repository.UserRepository;
import com.example.demo.infrastructure.security.JwtTokenService;
import com.example.demo.domain.error.Error.AccessDeniedException;
import com.example.demo.domain.service.user.AuthenticationService;
import com.example.demo.infrastructure.persistence.entity.user.UserEntity;
import com.example.demo.infrastructure.service.user.error.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenService jwtTokenService;
  private final UserLockService userLockService;

  @Override
  public Pair<String, String> checkAuthenticationAndGetToken(String mobile, String password) {
    UserEntity userEntity = userRepository.findByMobile(mobile)
        .orElseThrow(UnauthorizedException::new);

    checkUserIsBlocked(userEntity.getId());

    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
          userEntity.getMobile(),
          password
      ));
    } catch (BadCredentialsException e) {
      setUserWrongAttempt(userEntity.getId());

      throw new UnauthorizedException();
    }

    log.info("User logged in with id: {} and mobile: {}", userEntity.getId(), mobile);

    return generateToken(userEntity);
  }

  private Pair<String, String> generateToken(UserEntity userEntity) {
    String accessToken = jwtTokenService.generateToken(
        userEntity.getId(),
        userEntity.getFingerprint()
    );
    String refreshToken = jwtTokenService.generateRefreshToken(
        userEntity.getId(),
        userEntity.getFingerprint()
    );

    return Pair.of(accessToken, refreshToken);
  }

  private void checkUserIsBlocked(Long userId) {
    boolean isUserBlocked = userLockService.isUserLock(
        userId.toString(),
        UserLockEntity.Reason.WRONG_PASSWORD
    );

    if (isUserBlocked) {
      throw new AccessDeniedException(ErrorEnum.user_locked);
    }
  }

  private void setUserWrongAttempt(Long userId) {
    userLockService.lockUserIfExceedMaxAttempts(
        userId.toString(),
        UserLockEntity.Reason.WRONG_PASSWORD
    );
  }

}
