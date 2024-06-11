package com.example.demo.infrastructure.service.user.error;

import com.example.demo.domain.error.ErrorEnum;
import com.example.demo.domain.error.Error;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends Error {

  public UserNotFoundException() {
    super(ErrorEnum.user_not_found, HttpStatus.NOT_FOUND);
  }

}
