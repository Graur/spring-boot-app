package ru.springboot.example.springbootexample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.springboot.example.springbootexample.config.security.SecurityConfig;
import ru.springboot.example.springbootexample.model.Role;
import ru.springboot.example.springbootexample.model.User;
import ru.springboot.example.springbootexample.service.RoleService;
import ru.springboot.example.springbootexample.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class UsersController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
    public String homePage() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(ModelMap model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping(value = { "/user"}, method = RequestMethod.GET)
    public String userPage(ModelMap model, Principal principal) {
        String userName = principal.getName();
        model.addAttribute("message", "Hello, " + userName + "!");
        return "user";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView mav = new ModelAndView("users_list");
        List<User> listUsers = userService.getAllUsers();
        mav.addObject("listUsers", listUsers);
        return mav;
    }

    @RequestMapping(value = "/admin/insert", method = RequestMethod.GET)
    public ModelAndView insertUserForm() {
        ModelAndView mav = new ModelAndView("users_list");
        return mav;
    }

    @RequestMapping(value = "/admin/insert", method = RequestMethod.POST)
    public ModelAndView insertUser(@ModelAttribute User user, @RequestParam("role") String role) {
        Set<Role> roles = roleService.getSetOfRoles(role);
        user.setRoles(roles);
        user.setPassword(SecurityConfig.bCryptPasswordEncoder().encode(user.getPassword()));
        roleService.updateSetOfRoles(roles);
        userService.insertUser(user);
        return new ModelAndView("redirect:/admin");
    }

    @RequestMapping(value = {"/admin/delete"}, method = RequestMethod.GET)
    public ModelAndView deleteUser(@RequestParam("id") int id) {
        userService.deleteUser(id);
        return new ModelAndView("redirect:/admin");
    }

    @RequestMapping(value = "/admin/update", method = RequestMethod.GET)
    public ModelAndView updateUserForm(@ModelAttribute User user) {
        ModelAndView mav = new ModelAndView("users_list");
        int updateUserId = user.getId();
        User existingUser = userService.getUser(updateUserId);
        mav.addObject("user", existingUser);
        return mav;
    }

    @RequestMapping(value = "/admin/update", method = RequestMethod.POST)
    public ModelAndView updateUser(@RequestParam("id") int id,
                                   @RequestParam("name") String name,
                                   @RequestParam("login") String login,
                                   @RequestParam("password") String password,
                                   @RequestParam("role") String role) {
        User updatedUser = userService.getUser(id);
        Set<Role> roles = roleService.getSetOfRoles(role);

        updatedUser.setName(name);
        updatedUser.setLogin(login);
        updatedUser.setPassword(SecurityConfig.bCryptPasswordEncoder().encode(password));
        updatedUser.setRoles(roles);

        roleService.updateSetOfRoles(roles);
        userService.updateUser(updatedUser);

        return new ModelAndView("redirect:/admin");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutUser(){
        return "login";
    }

    @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
    public String accessDenied(ModelMap model, Principal principal){
        String userName = principal.getName();
        model.addAttribute("name", userName);
        return "access-denied";
    }
}
