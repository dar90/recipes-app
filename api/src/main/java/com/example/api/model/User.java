package com.example.api.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Data;

@Data
@Entity
@Table(name = "application_users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank(message = "Field 'user_id' cannot be null.")
    private Long id;

    @NotBlank(message = "Field 'login' cannot be null.")
    @Size(min = 5, max = 25, message = "Field 'login' shouldn't be lesser than 5 and greater than 25 signs.")
    private String login;

    @NotBlank(message = "Field 'password' cannot be null.")
    @Size(min = 5, max = 55, message = "Field 'password' shouldn't be lesser than 5 and greater than 55 signs.")
    private String password;

    @Email
    @Column(name = "email", columnDefinition = "VARCHAR(255)")
    private String email;

    @NotBlank(message = "Field 'email_confirmed' cannot be null.")
    private boolean emailConfirmed;

    @NotBlank(message = "Field 'blocked' cannot be null.")
    private boolean blocked;

    @NotBlank(message = "Field 'role' cannot be null.")
    private UserRole role;

    @OneToMany
    private List<Recipe> recipes;

    @OneToMany(mappedBy = "author")
    private List<Opinion> opinions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !blocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return emailConfirmed;
    }

}