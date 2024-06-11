package com.example.demo.api.controller.organization.admin;

import com.example.demo.domain.model.organization.Organization;
import com.example.demo.domain.usecase.organization.OrganizationCreationUseCase;
import com.example.demo.api.dto.organization.admin.CreateOrganizationRequest;
import com.example.demo.api.dto.organization.admin.OrganizationAdminDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('SYSADMIN')")
@RequestMapping("/api/v1/admin/organizations")
public class OrganizationCreationAdminController {

  private final OrganizationCreationUseCase organizationCreationUseCase;
  private final ModelMapper modelMapper;

  @PostMapping
  public ResponseEntity<OrganizationAdminDto> create(
      @Valid @RequestBody CreateOrganizationRequest request
  ) {

    Organization organization = Organization.builder()
        .ownerId(request.getOwnerId())
        .name(request.getName())
        .description(request.getDescription())
        .build();

    organization = organizationCreationUseCase.create(organization);

    return ResponseEntity.status(HttpStatus.CREATED).
        body(modelMapper.map(organization, OrganizationAdminDto.class));
  }

}
