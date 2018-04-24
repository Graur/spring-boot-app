package ru.springboot.example.springbootexample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.springboot.example.springbootexample.dao.RoleDAO;
import ru.springboot.example.springbootexample.model.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    RoleDAO roleDAO;

    @Override
    public void addRole(Role role) {
        roleDAO.save(role);
    }

    @Override
    public Role getRoleByRoleName(String roleName) {
        return roleDAO.findRoleByName(roleName);
    }

    @Override
    public Role getRoleById(int id) {
        return roleDAO.getOne(id);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDAO.findAll();
    }

    @Override
    public void updateRoles(Role role) {
        roleDAO.save(role);
    }

    @Override
    public void deleteRoleById(int id) {
        roleDAO.deleteById(id);
    }

    @Override
    public Set<Role> getSetOfRoles(String roles) {
        String[] rolesList = roles.split(",\\s");
        Set<Role> setRole = new HashSet<>();
        for (String s : rolesList) {
            setRole.add(getRoleByRoleName(s));
        }
        return setRole;
    }

    @Override
    public void updateSetOfRoles(Set<Role> roles) {
        for (Role role : roles)
            roleDAO.findRoleByName(role.getName());
    }
}
