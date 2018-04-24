package ru.springboot.example.springbootexample.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.springboot.example.springbootexample.model.Role;

import java.util.List;

@Repository
public interface RoleDAO extends JpaRepository<Role, Integer> {
    Role findRoleByName(String name);
}
