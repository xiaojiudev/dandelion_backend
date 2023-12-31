package com.dandelion.backend.controllers;


import com.dandelion.backend.payloads.ApiResponse;
import com.dandelion.backend.payloads.UserBody;
import com.dandelion.backend.payloads.dto.UserDTO;
import com.dandelion.backend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;


    // POST - create user
    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserBody userBody) {
        return new ResponseEntity<>(userService.createUser(userBody), HttpStatus.CREATED);
    }

    // PUT - update user
    @PutMapping("/users/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("userId") Long userId, @Valid @RequestBody UserBody userBody) {
        return new ResponseEntity<>(userService.updateUser(userId, userBody), HttpStatus.OK);
    }

    // DELETE - delete user
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Long userId) {

        userService.deleteUser(userId);

        return new ResponseEntity<>(new ApiResponse("User is deleted successfully", true), HttpStatus.OK);
    }

    //     GET a list of users
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    // GET a user
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

}
