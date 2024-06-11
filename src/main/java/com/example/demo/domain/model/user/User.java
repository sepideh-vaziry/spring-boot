package com.example.demo.domain.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private UserRole userRole;
    private Long exchangeUserId;
    private String firstName;
    private String lastName;
    private String mobile;
    private String nationalCode;
    private String password;

}
