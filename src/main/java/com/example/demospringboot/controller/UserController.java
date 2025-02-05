package com.example.demospringboot.controller;

import com.example.demospringboot.dto.request.UserRequest;
import com.example.demospringboot.dto.response.UserResponse;
import com.example.demospringboot.mapper.UserMapper;
import com.example.demospringboot.model.User;
import com.example.demospringboot.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest){
        var user = userService.createUser(userMapper.toModel(userRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toUserResponse(user));
    }

    @GetMapping
    public List<UserResponse> getAllUsers (){
        return userService.getAllUsers().stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userMapper.toUserResponse(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable int id, @RequestBody UserRequest userRequest){
        var user = userService.updateUser(id , userMapper.toModel(userRequest));
        return ResponseEntity.ok(userMapper.toUserResponse(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id){
        var user = userService.getUserById(id);
        if (user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
