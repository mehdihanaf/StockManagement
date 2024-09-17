package com.stock.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serial;
import java.util.Collection;

public class UserPrincipal extends User {

    @Serial
    private static final long serialVersionUID = -7028956164083621764L;

    public UserPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserPrincipal(String username, String password, boolean isEnabled, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, isEnabled, isEnabled, isEnabled, isEnabled, authorities);
    }

}

