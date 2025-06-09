package org.researchbucks.util.jwt;

import org.researchbucks.model.Respondent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class RespondentDetails implements UserDetails {

    private final Respondent respondent;

    public RespondentDetails(Respondent respondent){
        this.respondent = respondent;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(respondent.getRole().name()));
    }

    @Override
    public String getPassword() {
        return respondent.getPassword();
    }

    @Override
    public String getUsername() {
        return respondent.getEmail();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !(respondent.getIsLocked() || respondent.getIsDeleted());
    }

    @Override
    public boolean isEnabled() {
        return respondent.getIsVerified();
    }

    public Long getId(){ return respondent.getId(); }
}
