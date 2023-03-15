package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserDetailsServiceImpl userDetailsService;

    public UserController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "loginPage";
    }

//    @GetMapping()
//    public String userEndpoint(Model model, Authentication authentication) {
//        User loggedInUser = (User) authentication.getPrincipal();
//        model.addAttribute("user", loggedInUser);
//        return "user";
//    }
    @GetMapping("/user")
    public String showUser(Model model, Principal principal) {
        Optional<User> user = userDetailsService.findByEmail(principal.getName());
        model.addAttribute("user", userDetailsService.getUserById(user.get().getId()));
        System.out.println("Успешно: user id" + user.getClass());
        model.addAttribute("titleTable", "Страница пользователя: ");
        return "user";

}

}
