package org.researchbucks.AdminService_API.util.jwt;

import org.researchbucks.AdminService_API.model.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class AdminDetails implements UserDetails {

    private final Admin admin;

    public AdminDetails(Admin admin){
        this.admin = admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(admin.getRole().name()));
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return !admin.getIsDeleted();
    }

    public Long getId(){ return admin.getId(); }
}
