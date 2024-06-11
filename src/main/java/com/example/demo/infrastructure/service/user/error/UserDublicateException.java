package com.example.demo.infrastructure.service.user.error;

import com.example.demo.domain.error.ErrorEnum;
import com.example.demo.domain.error.Error;

public class UserDublicateException extends Error {

  public UserDublicateException() {
    super(ErrorEnum.user_duplication);
  }
}
