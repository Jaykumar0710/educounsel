package com.jk.educounsel.controller;

import com.jk.educounsel.model.Role;
import com.jk.educounsel.model.User;
import com.jk.educounsel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("user",new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model){
        try{
            Role role = Role.valueOf(user.getRole().toString());
            user.setRole(role);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Invalid role Selected");
            return "register";
        }
        userService.saveUser(user);

        return "redirect:/login";
    }
}
