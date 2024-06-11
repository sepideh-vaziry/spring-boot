package com.example.demo.infrastructure.service.user;

import com.example.demo.domain.service.user.CurrentUserService;
import com.example.demo.infrastructure.persistence.entity.user.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {

  @Override
  public Long getId() {
    Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (!(object instanceof UserEntity)) {
      return null;
    }

    return ((UserEntity) object).getId();
  }

}
