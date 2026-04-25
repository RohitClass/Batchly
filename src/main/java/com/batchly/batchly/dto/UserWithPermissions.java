package com.batchly.batchly.dto;

import com.batchly.batchly.entity.RolePermission;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class UserWithPermissions {

    private Long userId;
    private String email;
    private String password;
    private Long roleId;
    private String roleName;
    private List<RolePermission> permissions = new ArrayList<>();

    // Called by CustomUserDetailsService to build Spring Security authorities
    public Collection<GrantedAuthority> toAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();

        // Add role itself e.g. ROLE_TEACHER
        auths.add(new SimpleGrantedAuthority("ROLE_" + roleName));

        // Add each module permission e.g. grades:READ, grades:CREATE
        permissions.forEach(p -> {
            if (p.isCanCreate()) auths.add(new SimpleGrantedAuthority(p.getModuleName() + ":CREATE"));
            if (p.isCanRead())   auths.add(new SimpleGrantedAuthority(p.getModuleName() + ":READ"));
            if (p.isCanUpdate()) auths.add(new SimpleGrantedAuthority(p.getModuleName() + ":UPDATE"));
            if (p.isCanDelete()) auths.add(new SimpleGrantedAuthority(p.getModuleName() + ":DELETE"));
        });

        return auths;
    }
}