package com.example.demo.infrastructure.persistence.repository;

import com.example.demo.infrastructure.persistence.entity.user.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByMobile(String mobile);

}
