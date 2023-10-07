package com.dandelion.backend.services.impl;

import com.dandelion.backend.entities.Role;
import com.dandelion.backend.entities.User;
import com.dandelion.backend.entities.enumType.RoleBase;
import com.dandelion.backend.exceptions.ResourceAlreadyExistsException;
import com.dandelion.backend.exceptions.ResourceNotFoundException;
import com.dandelion.backend.payloads.UserBody;
import com.dandelion.backend.payloads.dto.UserDTO;
import com.dandelion.backend.repositories.RoleRepo;
import com.dandelion.backend.repositories.UserRepo;
import com.dandelion.backend.services.EncryptionService;
import com.dandelion.backend.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final ModelMapper modelMapper;
    private final EncryptionService encryptionService;

    @Override
    public UserDTO createUser(UserBody userBody) {

        Optional<User> tempUser = userRepo.findByEmailIgnoreCase(userBody.getEmail());

        if (tempUser.isPresent()) {
            throw new ResourceAlreadyExistsException("User already exist with id = " + tempUser.get().getId());
        }

        List<Role> userRoles = getUserRoleHelper(userBody.getRole());

        User user = User.builder()
                .email(userBody.getEmail())
                .phone(userBody.getPhone())
                .password(encryptionService.encryptPassword(userBody.getPassword()))
                .fullName(userBody.getFullName())
                .gender(userBody.getGender())
                .avatar(userBody.getAvatar())
                .roles(userRoles)
                .build();

        User savedUser = userRepo.save(user);

        UserDTO userResponse = modelMapper.map(savedUser, UserDTO.class);
        List<RoleBase> roleList = userRoles.stream().map(Role::getRoleName).collect(Collectors.toList());
        userResponse.setRoles(roleList);

        return userResponse;
    }


    @Override
    public UserDTO updateUser(Long userId, UserBody userBody) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id = " + userId));

        List<Role> userRoles = getUserRoleHelper(userBody.getRole());

        user.setEmail(userBody.getEmail());
        user.setPhone(userBody.getPhone());
        user.setPassword(userBody.getPassword());
        user.setFullName(userBody.getFullName());
        user.setGender(userBody.getGender());
        user.setAvatar(userBody.getAvatar());
        user.setRoles(userRoles);

        User updatedUser = userRepo.save(user);

        UserDTO userResponse = modelMapper.map(updatedUser, UserDTO.class);
        List<RoleBase> roleList = userRoles.stream().map(Role::getRoleName).collect(Collectors.toList());
        userResponse.setRoles(roleList);

        return userResponse;
    }

    @Override
    public UserDTO getUserById(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id = " + userId));

        UserDTO userResponse = modelMapper.map(user, UserDTO.class);
        List<RoleBase> userRoles = user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
        userResponse.setRoles(userRoles);

        return userResponse;
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

    private List<Role> getUserRoleHelper(String roleBody) {

        List<Role> userRoles = new ArrayList<>();
        if (roleBody == null) {
            roleBody = RoleBase.CUSTOMER.toString();
        }

        Role customerRole = roleRepo.findByRoleName(RoleBase.CUSTOMER)
                .orElseThrow(() -> new ResourceNotFoundException("Role not exist!"));
        userRoles.add(customerRole);

        if (roleBody.equalsIgnoreCase(RoleBase.ADMIN.toString())) {
            Role adminRole = roleRepo.findByRoleName(RoleBase.ADMIN)
                    .orElseThrow(() -> new ResourceNotFoundException("Role not exist!"));
            Role managerRole = roleRepo.findByRoleName(RoleBase.MANAGER)
                    .orElseThrow(() -> new ResourceNotFoundException("Role not exist!"));
            userRoles.add(adminRole);
            userRoles.add(managerRole);
        }

        if (roleBody.equalsIgnoreCase(RoleBase.MANAGER.toString())) {
            Role managerRole = roleRepo.findByRoleName(RoleBase.MANAGER)
                    .orElseThrow(() -> new ResourceNotFoundException("Role not exist!"));
            userRoles.add(managerRole);
        }

        return userRoles;
    }

}
