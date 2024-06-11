package com.example.demo.infrastructure.persistence.repository;

import com.example.demo.infrastructure.persistence.entity.user.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Integer> {



}
