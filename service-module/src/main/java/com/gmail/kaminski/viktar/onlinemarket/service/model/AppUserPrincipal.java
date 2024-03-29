package com.gmail.kaminski.viktar.onlinemarket.service.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AppUserPrincipal implements UserDetails {

    private UserAuthorizedDTO authorizedUserDTO;
    private Set<GrantedAuthority> grantedAuthorities;

    public AppUserPrincipal(UserAuthorizedDTO authorizedUserDTO) {
        this.authorizedUserDTO = authorizedUserDTO;
        this.grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(authorizedUserDTO.getRole().getName()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return authorizedUserDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return authorizedUserDTO.getEmail();
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

    public UserAuthorizedDTO getAuthorizedUserDTO() {
        return authorizedUserDTO;
    }
}
