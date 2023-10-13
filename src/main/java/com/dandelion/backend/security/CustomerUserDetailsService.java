package com.dandelion.backend.security;

import com.dandelion.backend.entities.User;
import com.dandelion.backend.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepo.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        
        return user;
    }
}
