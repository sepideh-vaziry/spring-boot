package com.example.demo.infrastructure.persistence.repository;

import com.example.demo.infrastructure.persistence.entity.user.UserLockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLockRepository extends JpaRepository<UserLockEntity, Long> {

  Optional<UserLockEntity> findByReasonAndUserColumn(UserLockEntity.Reason reason, String userColumn);

}
