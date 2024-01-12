package com.example.Triple_clone.dto.membership;

import com.example.Triple_clone.domain.entity.User;
import com.example.Triple_clone.domain.vo.Role;

public record UserJoinRequestDto(String name, String email, String password, String role) {
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .roles(Role.valueOf(role))
                .build();
    }
}
