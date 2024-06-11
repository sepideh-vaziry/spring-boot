package com.example.demo.api.controller.user.admin;

import com.example.demo.domain.model.user.User;
import com.example.demo.domain.model.user.UserRole;
import com.example.demo.domain.usecase.user.UserCreationUseCase;
import com.example.demo.api.dto.user.admin.UserCreationRequest;
import com.example.demo.api.dto.user.admin.UserAdminDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/users")
@PreAuthorize("hasAnyAuthority('SYSADMIN')")
public class UserCreationAdminController {

  private final UserCreationUseCase userCreationUseCase;
  private final ModelMapper modelMapper;

  @PostMapping
  public ResponseEntity<UserAdminDto> createUser(
      @RequestBody UserCreationRequest request
  ) {

      User user = User.builder()
          .mobile(request.getMobile())
          .firstName(request.getFirstName())
          .lastName(request.getLastName())
          .nationalCode(request.getNationalCode())
          .userRole(UserRole.builder()
              .id(request.getRoleIde())
              .build())
          .build();

      user = userCreationUseCase.create(user);

      return ResponseEntity.ok(modelMapper.map(user, UserAdminDto.class));
  }

}
