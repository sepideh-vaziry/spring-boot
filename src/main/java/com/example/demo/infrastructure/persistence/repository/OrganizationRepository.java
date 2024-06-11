package com.example.demo.infrastructure.persistence.repository;

import com.example.demo.infrastructure.persistence.entity.organization.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Long> {

  List<OrganizationEntity> findByOwnerId(Long ownerId);

}
