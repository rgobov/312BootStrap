package com.example.security.spring_security.seurity;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthProviderImpl implements AuthenticationProvider {
    private final UserDetailsService customsDetailsServise;
    private final PasswordEncoder passwordEncoder;

    public AuthProviderImpl(CustomsDetailsServise customsDetailsServise, PasswordEncoder passwordEncoder) {
        this.customsDetailsServise = customsDetailsServise;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("AuthProviderImpl: Attempting to authenticate user with email: " + authentication.getName()); // Логирование
        String email = authentication.getName();
        UserDetails userDetails = customsDetailsServise.loadUserByUsername(email);
        System.out.println("AuthProviderImpl: UserDetails loaded: " + userDetails);
        String password = authentication.getCredentials().toString();
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("AuthProviderImpl: Password does not match for user: " + email);
            throw new BadCredentialsException("Неверный пароль");
        }
        System.out.println("AuthProviderImpl: Authentication successful for user: " + email);
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}