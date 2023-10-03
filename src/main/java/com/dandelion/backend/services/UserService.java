package com.dandelion.backend.services;

import com.dandelion.backend.payloads.UserBody;
import com.dandelion.backend.payloads.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserBody user);

    UserDTO updateUser(Long userId, UserBody user);

    UserDTO getUserById(Long userId);

    List<UserDTO> getAllUsers();

    void deleteUser(Long userId);

}
