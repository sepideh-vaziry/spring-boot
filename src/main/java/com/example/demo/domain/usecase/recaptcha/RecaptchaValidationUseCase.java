package com.example.demo.domain.usecase.recaptcha;

import com.example.demo.domain.error.ErrorEnum;
import com.example.demo.infrastructure.service.recaptcha.error.InvalidReCaptchaException;
import com.example.demo.domain.service.recaptcha.ReCaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecaptchaValidationUseCase {

  private final ReCaptchaService recaptchaService;

  public void processWebResponse(String recaptchaResponse, String clientIp) {
    if (recaptchaResponse == null || recaptchaResponse.isBlank()) {
      throw new InvalidReCaptchaException(ErrorEnum.recaptcha_not_provided);
    }

    recaptchaService.processWebResponse(recaptchaResponse, clientIp);
  }

  public void processAndroidResponse(String recaptchaResponse, String clientIp) {
    if (recaptchaResponse == null || recaptchaResponse.isBlank()) {
      throw new InvalidReCaptchaException(ErrorEnum.recaptcha_not_provided);
    }

    recaptchaService.processAndroidResponse(recaptchaResponse, clientIp);
  }

  public void processIOSResponse(String recaptchaResponse, String clientIp) {
    if (recaptchaResponse == null || recaptchaResponse.isBlank()) {
      throw new InvalidReCaptchaException(ErrorEnum.recaptcha_not_provided);
    }

    recaptchaService.processIOSResponse(recaptchaResponse, clientIp);
  }

}
