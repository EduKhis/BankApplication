package com.example.bankapplication.controller;

import com.example.bankapplication.model.Role;
import com.example.bankapplication.model.User;
import com.example.bankapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    @GetMapping("registration")
    public String openRegistration (Model model){
        model.addAttribute("response", "");
        model.addAttribute("user", new User());
        return "registration";
    }
    @PostMapping("registration")
    public String registrationUser (Model model, @ModelAttribute("user") User user) {
        if (user.getUsername().isEmpty()) {
            model.addAttribute("response", "Input username");
        }
        else if (user.getPassword().isEmpty()) {
            model.addAttribute("response", "Input password");
        }
        else {
            if (!userRepository.existsByUsername(user.getUsername())) {
                user.setActive(true);
                List<Role> role = new ArrayList<>();
                role.add(Role.USER);
                user.setRoles(role);
                userRepository.save(user);
                return "login";

            }
            else {
                model.addAttribute("response", "This Username is used to");
            }
        }
        return "registration";
    }
}
