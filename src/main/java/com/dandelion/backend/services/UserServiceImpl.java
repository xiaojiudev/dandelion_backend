package com.dandelion.backend.services;

import com.dandelion.backend.entities.User;
import com.dandelion.backend.exceptions.UserNotFoundException;
import com.dandelion.backend.payloads.UserDTO;
import com.dandelion.backend.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        User user = dtoToUser(userDTO);

        User savedUser = userRepo.save(user);

        return userToDto(savedUser);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id = " + userId));


        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setPassword(userDTO.getPassword());
        user.setFullName(userDTO.getFullName());
        user.setGender(userDTO.getGender());
        user.setAvatar(userDTO.getAvatar());

        User updatedUser = userRepo.save(user);

        return userToDto(updatedUser);
    }

    @Override
    public UserDTO getUserById(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id = " + userId));

        return userToDto(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {

        List<User> users = userRepo.findAll();

        List<UserDTO> userDTOS = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());

        return userDTOS;
    }

    @Override
    public void deleteUser(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id = " + userId));

        userRepo.delete(user);


    }

    // Convert UserDTO => User
    private User dtoToUser(UserDTO userDto) {

        User user = modelMapper.map(userDto, User.class);

        return user;
    }

    // Conver User => UserDTO
    public UserDTO userToDto(User user) {

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return userDTO;
    }
}
