package org.example.jwtauth.security;

import org.example.jwtauth.model.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            final String name = authentication.getName();
            final String password = authentication.getCredentials().toString();

            if (!"ADMIN".equals(name)) {
                return null;
            }
            return getUPAtoken(name, password);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    private static UsernamePasswordAuthenticationToken getUPAtoken(String name, String password) {
        final List<GrantedAuthority> grantedAuths = new ArrayList<>();
        User user = new User(name, password, "ADMIN");
        grantedAuths.add(new SimpleGrantedAuthority("ADMIN"));
        return new UsernamePasswordAuthenticationToken(user, password, grantedAuths);
    }
}
