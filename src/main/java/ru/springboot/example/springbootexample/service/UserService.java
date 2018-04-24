package ru.springboot.example.springbootexample.service;

import ru.springboot.example.springbootexample.model.Role;
import ru.springboot.example.springbootexample.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getAllUsers();

    void insertUser(User user);

    void deleteUser(int id);

    void updateUser(User user);

    User getUser(int id);

    Set<Role> getUserRoles(String login);

    User getUserByLogin(String login);
}
