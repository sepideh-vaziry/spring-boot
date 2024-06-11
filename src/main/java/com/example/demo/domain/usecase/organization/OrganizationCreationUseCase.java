package com.example.demo.domain.usecase.organization;

import com.example.demo.domain.error.ErrorEnum;
import com.example.demo.domain.error.Error;
import com.example.demo.domain.model.organization.Organization;
import com.example.demo.domain.service.organization.OrganizationCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationCreationUseCase {

  private final OrganizationCreationService organizationCreationService;

  public Organization create(Organization organization) {
    if (organization.getOwnerId() == null) {
      throw new Error.RequiredFieldError(ErrorEnum.owner_id_not_provided);
    }

    if (organization.getName() == null || organization.getName().isBlank()) {
      throw new Error.RequiredFieldError(ErrorEnum.organization_name_not_provided);
    }

    return organizationCreationService.create(organization);
  }

}
