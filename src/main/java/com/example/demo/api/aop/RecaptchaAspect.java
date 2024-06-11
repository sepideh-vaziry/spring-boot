package com.example.demo.api.aop;

import com.example.demo.domain.error.ErrorEnum;
import com.example.demo.domain.usecase.recaptcha.RecaptchaValidationUseCase;
import com.example.demo.infrastructure.service.recaptcha.error.InvalidReCaptchaException;
import com.example.demo.api.util.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class RecaptchaAspect {

  private static final String CLIENT_TYPE_WEB = "web";
  private static final String CLIENT_TYPE_ANDROID = "android";
  private static final String CLIENT_TYPE_IOS = "ios";

  private final RecaptchaValidationUseCase recaptchaValidationUseCase;

  @Before("@annotation(com.example.demo.api.aop.CheckRecaptcha)")
  public void validateRecaptcha() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
        .getRequest();

    Pair<String, String> recaptchaResponse = extractRecaptchaResponse(request);

    switch (recaptchaResponse.getSecond()) {
      case CLIENT_TYPE_WEB -> recaptchaValidationUseCase.processWebResponse(
          recaptchaResponse.getFirst(),
          IpUtils.getUserIp(request)
      );

      case CLIENT_TYPE_ANDROID -> recaptchaValidationUseCase.processAndroidResponse(
          recaptchaResponse.getFirst(),
          IpUtils.getUserIp(request)
      );

      case CLIENT_TYPE_IOS -> recaptchaValidationUseCase.processIOSResponse(
          recaptchaResponse.getFirst(),
          IpUtils.getUserIp(request)
      );
    }

  }

  private Pair<String, String> extractRecaptchaResponse(HttpServletRequest request) {
    String captchaResponse;
    String clientType;

    if (request.getHeader("captcha-response") != null) {
      captchaResponse = request.getHeader("captcha-response");
      clientType = CLIENT_TYPE_WEB;
    } else if (request.getHeader("android-captcha-response") != null) {
      captchaResponse = request.getHeader("android-captcha-response");
      clientType = CLIENT_TYPE_ANDROID;
    } else if (request.getHeader("ios-captcha-response") != null) {
      captchaResponse = request.getHeader("ios-captcha-response");
      clientType = CLIENT_TYPE_IOS;
    } else {
      throw new InvalidReCaptchaException(ErrorEnum.recaptcha_not_provided);
    }

    return Pair.of(captchaResponse, clientType);
  }

}
