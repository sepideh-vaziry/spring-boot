package com.example.demo.api.dto.organization.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationAdminDto {

  private Long id;
  private Long ownerId;
  private String name;
  private String description;

}
