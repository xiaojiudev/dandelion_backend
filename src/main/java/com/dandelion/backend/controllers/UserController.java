package com.dandelion.backend.controllers;


import com.dandelion.backend.payloads.ApiResponse;
import com.dandelion.backend.payloads.UserDTO;
import com.dandelion.backend.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {

    @Autowired
    UserService userService;

    //     GET a list of users
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
//        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.ACCEPTED);

        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    // GET a user
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDTO> getUserDetailsHandler(@PathVariable("userId") Long userId) {
//        return new ResponseEntity<>(userService.getUserDetails(userId), HttpStatus.ACCEPTED);
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    // POST - create user

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    // PUT - update user
    @PutMapping("/users/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("userId") Long userId, @Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.updateUser(userId, userDTO), HttpStatus.OK);
    }

    // DELETE - delete user
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Long userId) {

        userService.deleteUser(userId);

        return new ResponseEntity<>(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
    }

}
