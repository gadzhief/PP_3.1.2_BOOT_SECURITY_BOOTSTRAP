package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping()
    public String userEndpoint(Model model, Authentication authentication) {
        User loggedInUser = (User) authentication.getPrincipal();
        model.addAttribute("user", loggedInUser);
        return "user";
    }

}
