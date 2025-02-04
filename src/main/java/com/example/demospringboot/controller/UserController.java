package com.example.demospringboot.controller;

import com.example.demospringboot.model.User;
import com.example.demospringboot.util.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final Map<Integer, User> userDatabase = new HashMap<>();
    private int userCountId = 1;

    @PostMapping
    public ApiResponse<String> createUser(@RequestBody User user){
        user.setId(userCountId++);
        userDatabase.put(user.getId(),user);
        return new ApiResponse<>(200, "User created successfully", user.getName());
    }

    @GetMapping("/{id}")
    public  ApiResponse<User> getUserById(@PathVariable int id){
        User user = userDatabase.get(id);
        if (user == null){
            return new ApiResponse<>(404, "User not found", null);
        }
        return new ApiResponse<>(200, "User found", user);
    }

    @GetMapping
    public ApiResponse<List<User>> getAllUsers (){
        List<User> users = new ArrayList<>(userDatabase.values());
        if (users.isEmpty()){
            return new ApiResponse<>(404, "No user found", null);
        }
        return new ApiResponse<>(200, "All users", users);
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updateUser(@PathVariable int id , @RequestBody User user){
        User updateUser = userDatabase.get(id);
        if (updateUser == null ){
            return new ApiResponse<>(404, "User not found", null);
        }
        updateUser.setName(user.getName());
        updateUser.setEmail(user.getEmail());
        updateUser.setPassword(user.getPassword());
        userDatabase.put(id, updateUser);
        return new ApiResponse<>(200, "User updated successfully", updateUser.getName());
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUser (@PathVariable int id ){
        User user = userDatabase.get(id);
        if (user == null){
            return new ApiResponse<>(404, "User not found", null);
        }
        userDatabase.remove(id);
        return new ApiResponse<>(200, "User deleted successfully with", user.getName());
    }
}
