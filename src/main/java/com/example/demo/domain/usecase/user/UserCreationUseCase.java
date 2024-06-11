package com.example.demo.domain.usecase.user;

import com.example.demo.domain.model.user.User;
import com.example.demo.domain.service.user.UserCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCreationUseCase {

  private final UserCreationService userCreationService;

  public User create(User user) {
    return userCreationService.create(user);
  }
}
