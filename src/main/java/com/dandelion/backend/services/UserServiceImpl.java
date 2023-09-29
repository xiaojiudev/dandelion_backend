package com.dandelion.backend.services;

import com.dandelion.backend.entities.User;
import com.dandelion.backend.exceptions.ResourceAlreadyExistsException;
import com.dandelion.backend.exceptions.ResourceNotFoundException;
import com.dandelion.backend.payloads.UserDTO;
import com.dandelion.backend.repositories.UserRepo;
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
    public UserDTO createUser(UserDTO userDTO) {

        User user = modelMapper.map(userDTO, User.class);

        Optional<User> tempUser = userRepo.findByEmailIgnoreCase(user.getEmail());

        if (tempUser.isPresent()) {
            throw new ResourceAlreadyExistsException("User already exist with id = " + tempUser.get().getId());
        }

        User savedUser = userRepo.save(user);

        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id = " + userId));


        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setPassword(userDTO.getPassword());
        user.setFullName(userDTO.getFullName());
        user.setGender(userDTO.getGender());
        user.setAvatar(userDTO.getAvatar());

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

        List<UserDTO> userDTOS = users.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());

        return userDTOS;
    }

    @Override
    public void deleteUser(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id = " + userId));

        userRepo.delete(user);


    }
    
}
