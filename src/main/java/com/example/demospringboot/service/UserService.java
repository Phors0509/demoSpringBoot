package com.example.demospringboot.service;

import com.example.demospringboot.model.User;
import com.example.demospringboot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAllByDeletedFalse();
    }

    public User getUserById(int id){
        User user = userRepository.findById(id).orElse(null);
        if (user == null || user.isDeleted()){
            return null;
        }
        return user;
    }

    public User updateUser(int id, User user){
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null){
            return null;
        }
        existingUser.setDeleted(true);
        userRepository.save(existingUser);
        user.setId(0);
        return userRepository.save(user);
    }

    public void deleteUser(int id){
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null){
            return;
        }
        existingUser.setDeleted(true);
        userRepository.save(existingUser);
    }

}
