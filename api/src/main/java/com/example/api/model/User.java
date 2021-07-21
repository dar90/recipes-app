package com.example.api.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String email;

    @NotBlank(message = "Field 'email_confirmed' cannot be null.")
    private boolean emailConfirmed;

    @NotBlank(message = "Field 'blocked' cannot be null.")
    private boolean blocked;

    @NotBlank(message = "Field 'role' cannot be null.")
    private UserRole role;

    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties("author")
    private List<Recipe> recipes;

    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties("author")
    private List<Opinion> opinions;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.toString()));
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return login;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return !blocked;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return emailConfirmed;
    }

}