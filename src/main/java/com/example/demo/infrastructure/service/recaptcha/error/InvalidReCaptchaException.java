package com.example.demo.infrastructure.service.recaptcha.error;

import com.example.demo.domain.error.ErrorEnum;
import com.example.demo.domain.error.Error;
import org.springframework.http.HttpStatus;

public class InvalidReCaptchaException extends Error {

  public InvalidReCaptchaException() {
    super(ErrorEnum.recaptcha_invalid, HttpStatus.BAD_REQUEST);
  }

  public InvalidReCaptchaException(ErrorEnum errorEnum) {
    super(errorEnum, HttpStatus.BAD_REQUEST);
  }

}
