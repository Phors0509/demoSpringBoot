package com.example.demospringboot.service;
import com.example.demospringboot.model.User;
import java.util.List;

public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();

    User getUserById(int id);

    User updateUser(int id, User user);

    void deleteUser(int id);

}

