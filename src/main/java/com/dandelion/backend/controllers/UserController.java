package com.dandelion.backend.controllers;


import com.dandelion.backend.entities.LocalUser;
import com.dandelion.backend.payloads.UserDTO;
import com.dandelion.backend.payloads.UserDetailDTO;
import com.dandelion.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {

    @Autowired
    UserService userService;

    // Handler to get a list of all users
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsersHandler() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.ACCEPTED);
    }

    // Handler to get a users
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDetailDTO> getUserDetailsHandler(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(userService.getUserDetails(userId), HttpStatus.ACCEPTED);
    }
}
