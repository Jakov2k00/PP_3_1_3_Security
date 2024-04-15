package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.services.RegistrationService;
import com.example.demo.until.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;

    private final UserValidator userValidator;

    @Autowired
    public AuthController(RegistrationService registrationService, UserValidator userValidator) {
        this.registrationService = registrationService;
        this.userValidator = userValidator;
    }

    @GetMapping("login")
    public String loginPageEndPoint() {
        return "security/login";
    }

    @GetMapping("registration")
    public String registrationPageEndPoint(@ModelAttribute("user") User user) {
        return "security/registration";
    }

    @PostMapping("/registration")
    public String performRegistrationEndPoint(@ModelAttribute("user") @Valid User user,
                                      BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "security/registration";
        }
        registrationService.registerUser(user);

        return "redirect:/auth/login";
    }
}
