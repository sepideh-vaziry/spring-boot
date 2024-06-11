package com.example.demo.infrastructure.service.user.error;

import com.example.demo.domain.error.ErrorEnum;
import com.example.demo.domain.error.Error;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends Error {

  public UnauthorizedException() {
    super(ErrorEnum.unauthorized, HttpStatus.UNAUTHORIZED);
  }

}
