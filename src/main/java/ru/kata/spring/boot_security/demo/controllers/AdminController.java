package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserDetailsServiceImpl userDetailsService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserDetailsServiceImpl userDetailsService,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String showAllUsers(Model model, Principal principal) {
        model.addAttribute("users", userDetailsService.getUsers());
        model.addAttribute("admin", userDetailsService.findByEmail(principal.getName()));
        model.addAttribute("newUser", new User());
        model.addAttribute("rolesAdd", roleRepository.findAll());
        return "admin";
    }

    @PostMapping("/create")
    public String adminCreateEndpoint(@ModelAttribute User user,
                                      @RequestParam(name = "roleNames", required = false) List<String> roleNames) {

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

    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute("user") User user,
                         @RequestParam(name = "roleNames", required = false) List<String> roleNames) {
        System.out.println(user);
        Set<Role> userRoles = roleRepository.findByNameIn(roleNames);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        user.setRoles(userRoles);
        userDetailsService.saveUser(user);
        return "redirect:/admin";
    }

}
