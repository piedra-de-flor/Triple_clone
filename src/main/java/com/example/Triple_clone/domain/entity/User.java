package com.example.Triple_clone.domain.entity;

import com.example.Triple_clone.domain.vo.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "userEntity")
@Getter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    //TODO
    //FIXME
    // - 추루에 Spring Security 와 함께 사용될 Role 필드
    // - 현재는 Spring filter만을 이용해서 token 검증
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    @Builder
    public User(String name, String email, String password, Role roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = new ArrayList<>();
        this.roles.add(roles);
        this.reviews = new ArrayList<>();
    }

    public void update(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> role.role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
