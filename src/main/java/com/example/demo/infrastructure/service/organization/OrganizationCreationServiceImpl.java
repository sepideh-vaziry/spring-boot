package com.example.demo.infrastructure.service.organization;

import com.example.demo.infrastructure.persistence.entity.organization.OrganizationEntity;
import com.example.demo.infrastructure.persistence.repository.OrganizationRepository;
import com.example.demo.domain.model.organization.Organization;
import com.example.demo.domain.service.organization.OrganizationCreationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationCreationServiceImpl implements OrganizationCreationService {

    private final OrganizationRepository organizationRepository;
    private final ModelMapper modelMapper;

    @Override
    public Organization create(Organization organization) {
        OrganizationEntity organizationEntity = OrganizationEntity.builder()
            .ownerId(organization.getOwnerId())
            .name(organization.getName())
            .description(organization.getDescription())
            .build();

        organizationRepository.save(organizationEntity);

        return modelMapper.map(organizationEntity, Organization.class);
    }

}
