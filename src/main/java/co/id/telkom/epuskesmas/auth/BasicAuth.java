package co.id.telkom.epuskesmas.auth;

import co.id.telkom.epuskesmas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BasicAuth implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String authUser = authentication.getName();
        String authPass = authentication.getCredentials().toString();

        if (userService.authUserByTeleponAndPassword(authUser, authPass)) {
            List<GrantedAuthority> authAuthority = new ArrayList<>();

            // TODO:
            // - Role Based Defined in Database not Hardcoded
            authAuthority.add(new SimpleGrantedAuthority("USER"));

            return new UsernamePasswordAuthenticationToken(authUser, authPass, authAuthority);
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
