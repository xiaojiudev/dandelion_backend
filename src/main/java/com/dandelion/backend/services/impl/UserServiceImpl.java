package com.dandelion.backend.services.impl;

import com.dandelion.backend.entities.Role;
import com.dandelion.backend.entities.User;
import com.dandelion.backend.entities.enumType.RoleBase;
import com.dandelion.backend.exceptions.ResourceAlreadyExistsException;
import com.dandelion.backend.exceptions.ResourceNotFoundException;
import com.dandelion.backend.payloads.UserBody;
import com.dandelion.backend.payloads.dto.UserDTO;
import com.dandelion.backend.repositories.UserRepo;
import com.dandelion.backend.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserBody userBody) {

        User user = modelMapper.map(userBody, User.class);

        System.out.println(userBody.getRoles());
        List<Role> roles = userBody.getRoles().stream().map(item -> {
            Role role = new Role();
            role.setRoleName(item);
            return role;
        }).collect(Collectors.toList());

        System.out.println(roles);
        user.setRoles(roles);

        Optional<User> tempUser = userRepo.findByEmailIgnoreCase(user.getEmail());

        if (tempUser.isPresent()) {
            throw new ResourceAlreadyExistsException("User already exist with id = " + tempUser.get().getId());
        }

        User savedUser = userRepo.save(user);

        return modelMapper.map(savedUser, UserDTO.class);
    }


    @Override
    public UserDTO updateUser(Long userId, UserBody userBody) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id = " + userId));


        user.setEmail(userBody.getEmail());
        user.setPhone(userBody.getPhone());
        user.setPassword(userBody.getPassword());
        user.setFullName(userBody.getFullName());
        user.setGender(userBody.getGender());
        user.setAvatar(userBody.getAvatar());

        User updatedUser = userRepo.save(user);

        return modelMapper.map(updatedUser, UserDTO.class);
    }

    @Override
    public UserDTO getUserById(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id = " + userId));

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {

        List<User> users = userRepo.findAll();

        List<UserDTO> userDTOS = users.stream().map(user -> {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);

            List<RoleBase> userRoles = user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
            userDTO.setRoles(userRoles);

            return userDTO;
        }).collect(Collectors.toList());

        return userDTOS;
    }

    @Override
    public void deleteUser(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id = " + userId));

        userRepo.delete(user);


    }

}
