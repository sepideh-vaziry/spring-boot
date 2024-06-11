package com.example.demo.infrastructure.service.organization;

import com.example.demo.infrastructure.persistence.entity.organization.OrganizationEntity;
import com.example.demo.infrastructure.persistence.repository.OrganizationRepository;
import com.example.demo.domain.model.organization.Organization;
import com.example.demo.domain.service.organization.UserOrganizationRetrievalService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserOrganizationRetrievalServiceImpl implements UserOrganizationRetrievalService {

  private final OrganizationRepository organizationRepository;
  private final ModelMapper modelMapper;

  @Override
  public List<Organization> get(Long userId) {
    return organizationRepository.findByOwnerId(userId)
        .stream()
        .map(this::map)
        .toList();
  }

  private Organization map(OrganizationEntity organizationEntity) {
    return modelMapper.map(organizationEntity, Organization.class);
  }

}
