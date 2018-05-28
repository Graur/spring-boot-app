package ru.springboot.example.springbootexample.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.springboot.example.springbootexample.model.Role;


@Repository
public interface RoleDAO extends JpaRepository<Role, Integer> {
    Role findRoleByName(String name);
}
