package org.researchbucks.ResearcherService_API.util.jwt;

import org.researchbucks.ResearcherService_API.model.Researcher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class ResearcherDetails implements UserDetails {

    private final Researcher researcher;

    public ResearcherDetails(Researcher researcher){
        this.researcher = researcher;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(researcher.getRole().name()));
    }

    @Override
    public String getPassword() {
        return researcher.getPassword();
    }

    @Override
    public String getUsername() {
        return researcher.getEmail();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !(researcher.getIsLocked() || researcher.getIsDeleted());
    }

    @Override
    public boolean isEnabled() {
        return researcher.getIsVerified();
    }

    public Long getUserId(){ return researcher.getId(); }
}
