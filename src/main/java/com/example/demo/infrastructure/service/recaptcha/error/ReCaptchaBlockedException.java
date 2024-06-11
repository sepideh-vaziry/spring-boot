package com.example.demo.infrastructure.service.recaptcha.error;

import com.example.demo.domain.error.ErrorEnum;
import com.example.demo.domain.error.Error;
import org.springframework.http.HttpStatus;

public class ReCaptchaBlockedException extends Error {

  public ReCaptchaBlockedException() {
    super(ErrorEnum.recaptcha_blocked, HttpStatus.FORBIDDEN);
  }

}
