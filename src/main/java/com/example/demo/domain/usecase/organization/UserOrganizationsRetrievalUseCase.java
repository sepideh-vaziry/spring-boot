package com.example.demo.domain.usecase.organization;

import com.example.demo.domain.model.organization.Organization;
import com.example.demo.domain.service.organization.UserOrganizationRetrievalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserOrganizationsRetrievalUseCase {

  private final UserOrganizationRetrievalService userOrganizationRetrievalService;
  
  public List<Organization> getUserOrganizations(Long userId) {
    return userOrganizationRetrievalService.get(userId);
  }

}
