package com.example.demo.infrastructure.service.recaptcha;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;

@Service
public class ReCaptchaAttemptService {

  private static final int MAX_ATTEMPTS = 5;
  private final LoadingCache<String, Integer> attemptsCache;

  public ReCaptchaAttemptService() {
    attemptsCache = CacheBuilder.newBuilder()
        .expireAfterWrite(4, TimeUnit.HOURS)
        .build(new CacheLoader<>() {
          @Override
          public Integer load(String key) {
            return 0;
          }
        });
  }

  public void reCaptchaSucceeded(String ip) {
    attemptsCache.invalidate(ip);
  }

  public void reCaptchaFailed(String ip) {
    int attempts = attemptsCache.getUnchecked(ip);
    attempts++;
    attemptsCache.put(ip, attempts);
  }

  public boolean isBlocked(String key) {
    return attemptsCache.getUnchecked(key) >= MAX_ATTEMPTS;
  }

}
