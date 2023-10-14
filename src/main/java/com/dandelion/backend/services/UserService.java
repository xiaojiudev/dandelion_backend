package com.dandelion.backend.services;

import com.dandelion.backend.entities.Role;
import com.dandelion.backend.entities.User;
import com.dandelion.backend.payloads.BearerToken;
import com.dandelion.backend.payloads.LoginRequest;
import com.dandelion.backend.payloads.RegistrationRequest;
import com.dandelion.backend.payloads.UserBody;
import com.dandelion.backend.payloads.dto.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    BearerToken authenticate(LoginRequest loginRequest);

    ResponseEntity<?> register(RegistrationRequest registrationRequest);

    Role saveRole(Role role);

    User saveUser(User user);

    UserDTO createUser(UserBody user);

    UserDTO updateUser(Long userId, UserBody user);

    UserDTO getUserById(Long userId);

    List<UserDTO> getAllUsers();

    void deleteUser(Long userId);
}
