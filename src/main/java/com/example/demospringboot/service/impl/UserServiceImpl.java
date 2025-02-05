package com.example.demospringboot.service.impl;

import com.example.demospringboot.exception.ResourceNotFoundException;
import com.example.demospringboot.model.User;
import com.example.demospringboot.repository.UserRepository;
import com.example.demospringboot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user){
        return userRepository.save(user);
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAllByDeletedFalse();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id)
                .filter(user -> !user.isDeleted())
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("User not found with id: " + id);
                });
    }

    @Override
    public User updateUser(int id, User user){
        User existingUser = userRepository.findById(id)
                .filter(isUser -> !isUser.isDeleted())
                .orElseThrow(() -> {
            return new ResourceNotFoundException("User not found with id: " + id);
        });
        existingUser.setDeleted(true);
        userRepository.save(existingUser);
        user.setId(0);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(int id){
        User existingUser = userRepository.findById(id)
                .filter(isUser -> !isUser.isDeleted())
                .orElseThrow(() -> { return new ResourceNotFoundException("User not found with id: " + id); });
        existingUser.setDeleted(true);
        userRepository.save(existingUser);
    }
}
