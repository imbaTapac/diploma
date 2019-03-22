package com.student.rating.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    STUDENT("STUDENT"),
    HEAD_OF_GROUP("HEAD_OF_GROUP"),
    HEAD_OF_SO("HEAD_OF_SO"),
    ADMINISTRATOR("ADMINISTRATOR");

    private final String authority;

    Role(String roleName) {
        this.authority = roleName;
    }


    @Override
    public String getAuthority() {
        return "ROLE_" + this.authority;
    }
}
