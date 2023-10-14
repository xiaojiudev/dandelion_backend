package com.dandelion.backend.utils;

import com.dandelion.backend.entities.User;
import com.dandelion.backend.exceptions.UserNotAuthenticatedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserUtil {

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("authentication: " + authentication);

        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            throw new UserNotAuthenticatedException("User is not authenticated!");
        }

        return (User) authentication.getPrincipal();
    }
}
