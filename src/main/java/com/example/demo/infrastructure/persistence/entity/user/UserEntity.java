package com.example.demo.infrastructure.persistence.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "user")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserRoleEntity role;

    private Long exchangeUserId;
    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String mobile;
    private String nationalCode;
    private String password;

    private String fingerprint;

    @CreationTimestamp
    @Column(columnDefinition = "DATETIME(3)")
    protected ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(columnDefinition = "DATETIME(3)")
    protected ZonedDateTime updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] userRoles = new String[] { role.getName() };

        return AuthorityUtils.createAuthorityList(userRoles);
    }

    @Override
    public String getUsername() {
        return mobile;
    }
}
