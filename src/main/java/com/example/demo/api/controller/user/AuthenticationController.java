package com.example.demo.api.controller.user;

import com.example.demo.domain.usecase.user.UserAuthenticationUseCase;
import com.example.demo.api.aop.CheckRecaptcha;
import com.example.demo.api.dto.user.AuthenticationRequest;
import com.example.demo.api.dto.user.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

  private final UserAuthenticationUseCase userAuthenticationUseCase;

  @PostMapping
  @CheckRecaptcha
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    Pair<String, String> tokens = userAuthenticationUseCase.authenticate(
        request.getMobile(),
        request.getPassword()
    );

    AuthenticationResponse response = AuthenticationResponse.builder()
        .accessToken(tokens.getFirst())
        .refreshToken(tokens.getSecond())
        .build();

    return ResponseEntity.ok(response);
  }

}
