package com.example.demo.api.controller.organization;

import com.example.demo.domain.model.organization.Organization;
import com.example.demo.domain.usecase.organization.UserOrganizationsRetrievalUseCase;
import com.example.demo.domain.usecase.user.CurrentUserUseCase;
import com.example.demo.api.dto.organization.OrganizationDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/organizations")
public class OrganizationRetrievalController {

  private final UserOrganizationsRetrievalUseCase userOrganizationsRetrievalUseCase;
  private final CurrentUserUseCase currentUserUseCase;
  private final ModelMapper modelMapper;

  @GetMapping("/me")
  public ResponseEntity<List<OrganizationDto>> getUserOrganizations() {
    Long currentUserId = currentUserUseCase.getId();

    List<OrganizationDto> organizations = userOrganizationsRetrievalUseCase.getUserOrganizations(currentUserId)
        .stream()
        .map(this::mapToDto)
        .toList();

    return ResponseEntity.ok(organizations);
  }

  private OrganizationDto mapToDto(Organization organization) {
    return modelMapper.map(organization, OrganizationDto.class);
  }

}
