package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserDetailsServiceImpl userDetailsService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserDetailsServiceImpl userDetailsService, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String adminEndpoint(Model model, Authentication authentication) {
        User loggedInUser = (User) authentication.getPrincipal();
        List<User> users = userDetailsService.getUsers();
        model.addAttribute("users", loggedInUser);
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/boot")
    public String adminEndpointBoot(Model model, Authentication authentication) {
        String loggedInUsername = authentication.getName();
        List<User> users = userDetailsService.getUsers();
        model.addAttribute("loggedInUsername", loggedInUsername);
        model.addAttribute("users", users);
        return "admin_boot";
    }

    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("users", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "create";
    }

    @PostMapping("/create")
    public String adminCreateEndpoint(@ModelAttribute User user, @RequestParam(name = "roleNames", required = false) List<String> roleNames) {

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Set<Role> userRoles = roleRepository.findByNameIn(roleNames);
        user.setRoles(userRoles);

        userDetailsService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userDetailsService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String updateUserForm(Model model, @PathVariable("id") Long id) {

        model.addAttribute("user", userDetailsService.getUserById(id));
        model.addAttribute("roles", roleRepository.findAll());
        return "update-user";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute("user") User user,
                         @RequestParam(name = "roleNames", required = false) List<String> roleNames) {

        Set<Role> userRoles = roleRepository.findByNameIn(roleNames);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        user.setRoles(userRoles);
        userDetailsService.saveUser(user);
        return "redirect:/admin";
    }

}
