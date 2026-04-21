package com.batchly.batchly.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.batchly.batchly.entity.RolePermission;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWithPermissions {
    private Long userId;
    private String email;
    private String password;
    private Long roleId;
    private String roleName;
    private List<RolePermission> permissions = new ArrayList<>();

    public Collection<GrantedAuthority> toAuthorities(){
        List<GrantedAuthority> auths = new ArrayList<>();
        auths.add(new SimpleGrantedAuthority("ROLE_"+roleName));
       permissions.forEach(p -> {
            if (p.isCanCreate()) auths.add(new SimpleGrantedAuthority(p.getModuleName() + ":CREATE"));
            if (p.isCanRead())   auths.add(new SimpleGrantedAuthority(p.getModuleName() + ":READ"));
            if (p.isCanUpdate()) auths.add(new SimpleGrantedAuthority(p.getModuleName() + ":UPDATE"));
            if (p.isCanDelete()) auths.add(new SimpleGrantedAuthority(p.getModuleName() + ":DELETE"));
        });
        return auths;
    }
}
