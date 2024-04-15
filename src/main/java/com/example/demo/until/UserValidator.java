package com.example.demo.until;

import com.example.demo.models.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (userService.loadUserByFirstName(user.getFirstName()).isPresent()
                && !userService.loadUserByFirstName(user.getFirstName()).orElse(null).equals(target)) {
            errors.rejectValue("firstName", "", "A user with that name already exists");
        }
    }
}