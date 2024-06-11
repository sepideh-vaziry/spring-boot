package com.example.demo.domain.service.user;

import org.springframework.data.util.Pair;

public interface AuthenticationService {

  Pair<String, String> checkAuthenticationAndGetToken(String mobile, String password);

}
