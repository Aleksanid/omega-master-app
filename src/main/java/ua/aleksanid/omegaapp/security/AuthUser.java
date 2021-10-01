package ua.aleksanid.omegaapp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AuthUser extends User {

    private Long id;

    public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Long id) {
        super(username, password, authorities);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
