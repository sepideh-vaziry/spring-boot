package com.example.demo.infrastructure.service.user;

import com.example.demo.infrastructure.persistence.entity.user.UserLockEntity;
import com.example.demo.infrastructure.persistence.entity.user.UserLockEntity.Reason;
import com.example.demo.infrastructure.persistence.repository.UserLockRepository;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLockService {

  private final static int USER_LOCK_DURATION_IN_MINUTES = 30; // Minutes
  private final static int WRONG_INTERVAL_IN_MINUTES = 5; // Minutes
  private final static int MAX_ATTEMPTS = 3;

  private final UserLockRepository userLockRepository;

  public boolean isUserLock(String userColumn, Reason reason) {
    UserLockEntity userLock = userLockRepository.findByReasonAndUserColumn(reason, userColumn)
        .orElse(null);

    return userLock != null && userLock.getLockUntil() != null && userLock.getLockUntil().isAfter(ZonedDateTime.now());
  }

  public void lockUserIfExceedMaxAttempts(String userColumn, UserLockEntity.Reason reason) {
    UserLockEntity userLock = userLockRepository.findByReasonAndUserColumn(reason, userColumn)
        .orElse(null);

    if (userLock == null) {
      userLock = UserLockEntity.builder()
          .reason(reason)
          .userColumn(userColumn)
          .count(1)
          .firstWrongAt(ZonedDateTime.now())
          .build();
    } else if (userLock.getFirstWrongAt().isAfter(ZonedDateTime.now().minusMinutes(WRONG_INTERVAL_IN_MINUTES))) {
      userLock.setCount(userLock.getCount() + 1);
    } else {
      userLock.setCount(1);
      userLock.setFirstWrongAt(ZonedDateTime.now());
    }

    if (userLock.getCount() >= MAX_ATTEMPTS) {
      userLock.setLockUntil(ZonedDateTime.now().plusMinutes(USER_LOCK_DURATION_IN_MINUTES));
    }

    userLockRepository.save(userLock);
  }

}
