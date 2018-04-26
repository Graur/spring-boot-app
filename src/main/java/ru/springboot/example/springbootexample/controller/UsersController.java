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
    public String homePage(ModelMap model) {
        //logger.info("index page");
        return "login";
    }

    // Login form with error
    @RequestMapping("/login-error")
    public String loginError(ModelMap model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    //URL ="user", write the message
    @RequestMapping(value = { "/user"}, method = RequestMethod.GET)
    public String userPage(ModelMap model, Principal principal) {
        String userName = principal.getName();
        model.addAttribute("message", "Hello, " + userName + "!");
        //logger.info("user page");
        return "user";
    }

    //URL ="admin", list all users
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView mav = new ModelAndView("users_list");
        List<User> listUsers = userService.getAllUsers();
        mav.addObject("listUsers", listUsers);
        //logger.info("admin page");
        return mav;
    }

    //URL ="insert", add user
    @RequestMapping(value = "/admin/insert", method = RequestMethod.GET)
    public ModelAndView insertUserForm() {
        ModelAndView mav = new ModelAndView("users_list");
        //logger.info("add user form");
        return mav;
    }

    @RequestMapping(value = "/admin/insert", method = RequestMethod.POST)
    public ModelAndView insertUser(@ModelAttribute User user, @RequestParam("role") String role) {
        Set<Role> roles = roleService.getSetOfRoles(role);
        user.setRoles(roles);
        user.setPassword(SecurityConfig.bCryptPasswordEncoder().encode(user.getPassword()));
        roleService.updateSetOfRoles(roles);
        userService.insertUser(user);
        //logger.info("----- ADD USER: User role: " + user.getRoles() + ". And user info is: " + user.toString());
        return new ModelAndView("redirect:/admin");
    }

    //URL ="/delete", delete user
    @RequestMapping(value = {"/admin/delete"}, method = RequestMethod.GET)
    public ModelAndView deleteUser(@RequestParam("id") int id) {
        userService.deleteUser(id);
        //logger.info("----- DELETE USER: user with id: " + id + " - was deleted");
        return new ModelAndView("redirect:/admin");
    }

    //URL ="update", update user
    @RequestMapping(value = "/admin/update", method = RequestMethod.GET)
    public ModelAndView updateUserForm(@ModelAttribute User user) {
        ModelAndView mav = new ModelAndView("users_list");
        int updateUserId = user.getId();
        User existingUser = userService.getUser(updateUserId);
        mav.addObject("user", existingUser);
        //logger.info("----- UPDATE USER FORM ----- OLD user info is: " + existingUser.toString());
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

        //logger.info("----- UPDATED USER: NEW user info is: " + updatedUser.toString() + ". With gifted roles: " + roles);
        return new ModelAndView("redirect:/admin");
    }

    //URL ="logout", write the message
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutUser(){

        //logger.info("+++ LOGOUT +++");
        return "login";
    }
    @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
    public String accessDenied(ModelMap model, Principal principal){
        String userName = principal.getName();
        model.addAttribute("name", userName);
       // logger.warn("!!! WARNING !!! USER " + userName + " TRY TO GET ACCESS TO ADMINs PAGES");
        return "access-denied";
    }
}
