package com.example.demo.infrastructure.service.recaptcha.error;

import com.example.demo.domain.error.ErrorEnum;
import com.example.demo.domain.error.Error;
import org.springframework.http.HttpStatus;

public class UnavailableReCaptchaException extends Error {

  public UnavailableReCaptchaException() {
    super(ErrorEnum.recaptcha_unavailable, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
