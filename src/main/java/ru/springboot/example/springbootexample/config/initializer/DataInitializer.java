package ru.springboot.example.springbootexample.config.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import ru.springboot.example.springbootexample.config.security.SecurityConfig;
import ru.springboot.example.springbootexample.model.Role;
import ru.springboot.example.springbootexample.model.User;
import ru.springboot.example.springbootexample.service.RoleService;
import ru.springboot.example.springbootexample.service.UserService;

import java.util.HashSet;
import java.util.Set;

public class DataInitializer {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    private void init() throws Exception {

        Role roleAdmin = new Role();
        roleAdmin.setName("ADMIN");
        roleService.addRole(roleAdmin);

        Role roleUser = new Role();
        roleUser.setName("USER");
        roleService.addRole(roleUser);

        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword(SecurityConfig.bCryptPasswordEncoder().encode("admin"));
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(roleAdmin);
        adminRoles.add(roleUser);
        admin.setRoles(adminRoles);

        userService.insertUser(admin);

        User user = new User();
        user.setLogin("user");
        user.setPassword(SecurityConfig.bCryptPasswordEncoder().encode("user"));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleUser);
        user.setRoles(userRoles);

        userService.insertUser(user);

    }
}
