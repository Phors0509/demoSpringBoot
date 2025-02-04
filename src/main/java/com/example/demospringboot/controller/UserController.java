package com.example.demospringboot.controller;

import com.example.demospringboot.model.User;
import com.example.demospringboot.repository.UserRepository;
import com.example.demospringboot.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final Map<Integer, User> userDatabase = new HashMap<>();

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ApiResponse<String> createUser(@RequestBody User user){
        userRepository.save(user);
        return new ApiResponse<>(200, "User created successfully", user.getName());
    }

    @GetMapping("/{id}")
    public  ApiResponse<User> getUserById(@PathVariable int id){
        User user = userRepository.findById(id).orElse(null);
        if (user == null){
            return new ApiResponse<>(404, "User not found", null);
        }
        return new ApiResponse<>(200, "User found", user);
    }

    @GetMapping
    public ApiResponse<List<User>> getAllUsers (){
        List<User> users = userRepository.findAll();
        if (users.isEmpty()){
            return new ApiResponse<>(404, "No user found", null);
        }
        return new ApiResponse<>(200, "All users", users);
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updateUser(@PathVariable int id , @RequestBody User user){
        Optional<User> updateUser = userRepository.findById(id);
        if (updateUser.isEmpty()){
            return new ApiResponse<>(404, "User not found", null);
        }
        User existingUser = updateUser.get();
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        userRepository.save(existingUser);
        return new ApiResponse<>(200, "User updated successfully", existingUser.getName());
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUser (@PathVariable int id ){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            return new ApiResponse<>(404, "User not found", null);
        }
        userRepository.deleteById(id);
        return new ApiResponse<>(200, "User deleted successfully", null);
    }
}
