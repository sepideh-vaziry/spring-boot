package com.example.demo.domain.service.organization;

import com.example.demo.domain.model.organization.Organization;

import java.util.List;

public interface UserOrganizationRetrievalService {
  
  List<Organization> get(Long userId);
  
}
