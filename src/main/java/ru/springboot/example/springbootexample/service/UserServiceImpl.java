package ru.springboot.example.springbootexample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.springboot.example.springbootexample.dao.UsersDAO;
import ru.springboot.example.springbootexample.model.Role;
import ru.springboot.example.springbootexample.model.User;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersDAO usersDAO;

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return usersDAO.findAll();
    }

    @Override
    @Transactional
    public void insertUser(User user) {
        this.usersDAO.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        this.usersDAO.deleteById(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        this.usersDAO.save(user);
    }

    @Override
    @Transactional
    public User getUser(int id) {
        return this.usersDAO.getOne(id);
    }

    public Set<Role> getUserRoles(String login) {
        return usersDAO.findUserByLogin(login).getRoles();
    }

    @Override
    public User getUserByLogin(String login) {
        return usersDAO.findUserByLogin(login);
    }

}
