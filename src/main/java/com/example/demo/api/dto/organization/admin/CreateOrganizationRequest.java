package com.example.demo.api.dto.organization.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrganizationRequest {

  @NotNull
  private Long ownerId;
  @NotBlank
  private String name;
  private String description;

}
