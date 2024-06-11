package com.example.demo.domain.usecase.user;

import com.example.demo.domain.service.user.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserUseCase {

  private final CurrentUserService currentUserService;

  public Long getId() {
    return currentUserService.getId();
  }

}
