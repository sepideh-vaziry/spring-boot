package com.example.demo.infrastructure.persistence.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(
    name = "user_lock",
    uniqueConstraints = {
        @UniqueConstraint(name = "uc_user_column_reason", columnNames = {"reason", "userColumn"})
    }
)
public class UserLockEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private Reason reason;
  private String userColumn;
  private int count;

  @Column(columnDefinition = "DATETIME(3)")
  private ZonedDateTime firstWrongAt;

  @Column(columnDefinition = "DATETIME(3)")
  private ZonedDateTime lockUntil;

  public enum Reason {
    WRONG_PASSWORD,
    WRONG_FORGOT_PASSWORD_CODE
  }

}
