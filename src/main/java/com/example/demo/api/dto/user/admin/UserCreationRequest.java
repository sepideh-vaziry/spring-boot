package com.example.demo.api.dto.user.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationRequest {

  private String firstName;
  private String lastName;
  private String nationalCode;
  private String mobile;
  private int roleIde;

}
