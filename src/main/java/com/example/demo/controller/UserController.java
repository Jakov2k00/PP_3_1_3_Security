package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public String getUsersEndpoint(@RequestParam(name = "count", required = false, defaultValue = "5")Integer count, Model model) {
        if (count == null || count >= 5) {
            model.addAttribute("users", userService.getAllUsers());
        } else {
            model.addAttribute("users", userService.getUsersByCount(count));
        }
        return "users";
    }

    @PostMapping(value = "/add")
    public String addUserEndpoint(@RequestParam String name, @RequestParam String surname, @RequestParam Byte age, @RequestParam String email) {
        userService.addUser(new User(name, surname, age, email));
        return "redirect:/users";
    }

    @GetMapping(value = "/delete")
    public String deleteUserEndpoint(@RequestParam(name = "id", required = false) Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/edit")
    public String editUserGetEndpoint(@RequestParam Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "edit-user";
    }

    @PostMapping("/edit")
    public String editUserPostEndpoint(@ModelAttribute User user) {
        userService.editUser(user);
        return "redirect:/users";
    }
}