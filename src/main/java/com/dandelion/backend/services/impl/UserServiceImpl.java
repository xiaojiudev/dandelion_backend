package com.dandelion.backend.services.impl;

import com.dandelion.backend.entities.Role;
import com.dandelion.backend.entities.User;
import com.dandelion.backend.entities.enumType.RoleBase;
import com.dandelion.backend.exceptions.ResourceAlreadyExistsException;
import com.dandelion.backend.exceptions.ResourceNotFoundException;
import com.dandelion.backend.payloads.BearerToken;
import com.dandelion.backend.payloads.LoginRequest;
import com.dandelion.backend.payloads.RegistrationRequest;
import com.dandelion.backend.payloads.UserBody;
import com.dandelion.backend.payloads.dto.UserDTO;
import com.dandelion.backend.repositories.RoleRepo;
import com.dandelion.backend.repositories.UserRepo;
import com.dandelion.backend.security.JwtUtilities;
import com.dandelion.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final AuthenticationManager authenticationManager;

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtilities jwtUtilities;

    private final ModelMapper modelMapper;

    @Override
    public BearerToken authenticate(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepo.findByEmailIgnoreCase(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        List<String> roleNames = new ArrayList<>();
        user.getRoles().forEach(role -> roleNames.add(role.getRoleName().toString()));

        String token = jwtUtilities.generateToken(user.getEmail(), roleNames);

        return new BearerToken(token, "Bearer");
    }

    @Override
    public ResponseEntity<?> register(RegistrationRequest registrationRequest) {

        if (userRepo.existsByEmailIgnoreCase(registrationRequest.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.SEE_OTHER);
        } else {
            User user = new User();
            user.setEmail(registrationRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setPhone(registrationRequest.getPhone());
            user.setFullName(registrationRequest.getFullName());

            //By Default , he/she is a simple user
            Role role = roleRepo.findByRoleName(RoleBase.CUSTOMER);

            user.setRoles(Collections.singletonList(role));

            userRepo.save(user);

            String token = jwtUtilities.generateToken(registrationRequest.getEmail(), Collections.singletonList(role.getRoleName().toString()));

            return new ResponseEntity<>(new BearerToken(token, "Bearer "), HttpStatus.OK);
        }
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public User saveUser(User user) {
        return userRepo.save(user);
    }


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
                .password(passwordEncoder.encode(userBody.getPassword()))
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

        Role customerRole = roleRepo.findByRoleName(RoleBase.CUSTOMER);
        userRoles.add(customerRole);

        if (roleBody.equalsIgnoreCase(RoleBase.ADMIN.toString())) {
            Role adminRole = roleRepo.findByRoleName(RoleBase.ADMIN);

            Role managerRole = roleRepo.findByRoleName(RoleBase.MANAGER);

            userRoles.add(adminRole);
            userRoles.add(managerRole);
        }

        if (roleBody.equalsIgnoreCase(RoleBase.MANAGER.toString())) {
            Role managerRole = roleRepo.findByRoleName(RoleBase.MANAGER);
            userRoles.add(managerRole);
        }

        return userRoles;
    }

}
