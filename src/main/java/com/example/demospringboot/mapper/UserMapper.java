package com.example.demospringboot.mapper;


import com.example.demospringboot.dto.UserRequest;
import com.example.demospringboot.dto.UserResponse;
import com.example.demospringboot.model.User;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toUserResponse(@NonNull User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }

    public User toModel(@NonNull UserRequest userRequest){
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        return user;
    }

}
