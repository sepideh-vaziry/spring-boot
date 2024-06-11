package com.example.demo.infrastructure.service.user;

import com.example.demo.domain.error.ErrorEnum;
import com.example.demo.infrastructure.persistence.entity.user.UserRoleEntity;
import com.example.demo.infrastructure.persistence.repository.UserRepository;
import com.example.demo.infrastructure.persistence.repository.UserRoleRepository;
import com.example.demo.domain.error.Error.NotFoundException;
import com.example.demo.domain.model.user.User;
import com.example.demo.domain.service.user.UserCreationService;
import com.example.demo.infrastructure.persistence.entity.user.UserEntity;
import com.example.demo.infrastructure.service.user.error.UserDublicateException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCreationServiceImpl implements UserCreationService {

  private final UserRepository userRepository;
  private final UserRoleRepository userRoleRepository;
  private final ModelMapper modelMapper;

  @Override
  public User create(User userModel) {

    UserEntity user = userRepository.findByMobile(userModel.getMobile()).orElse(null);
    if (user != null) {
      throw new UserDublicateException();
    }

    UserRoleEntity roleEntity = userRoleRepository.findById(userModel.getUserRole().getId())
        .orElseThrow(() -> new NotFoundException(ErrorEnum.user_role_not_found));
    UserEntity newUser = UserEntity.builder()
        .mobile(userModel.getMobile())
        .firstName(userModel.getFirstName())
        .lastName(userModel.getLastName())
        .role(roleEntity)
        .build();

    userRepository.save(newUser);
    return modelMapper.map(newUser, User.class);
  }
}
