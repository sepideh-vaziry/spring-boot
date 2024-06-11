package com.example.demo.domain.usecase.user;

import com.example.demo.domain.error.ErrorEnum;
import com.example.demo.domain.error.Error.RequiredFieldError;
import com.example.demo.domain.service.user.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthenticationUseCase {

  private final AuthenticationService authenticationService;

  public Pair<String, String> authenticate(String mobile, String password) {
    if (mobile == null || mobile.isBlank()) {
      throw new RequiredFieldError(ErrorEnum.mobile_or_password_not_provided);
    }

    if (password == null || password.isBlank()) {
      throw new RequiredFieldError(ErrorEnum.mobile_or_password_not_provided);
    }

    return authenticationService.checkAuthenticationAndGetToken(mobile, password);
  }

}
