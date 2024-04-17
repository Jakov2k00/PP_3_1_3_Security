package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.services.RoleService;
import com.example.demo.services.UserService;
import com.example.demo.until.RoleValidator;
import com.example.demo.until.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    private final UserValidator userValidator;

    private final RoleValidator roleValidator;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, UserValidator userValidator, RoleValidator roleValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
        this.roleValidator = roleValidator;
    }

    @GetMapping("/users")
    public String getAllUsersEndPoint(Model model, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        User user = userService.getUserByFirstName(principal.getName());
        model.addAttribute("user", user);
        List<User> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);
        return "admin/users";
    }

    @GetMapping("admin/removeUser")
    public String deleteUserEndPoint(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/updateUser")
    public String getEditUserFormEndPoint(Model model, @RequestParam("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/userUpdate";
    }

    @PostMapping("/updateUser")
    public String postEditUserFormEndPoint(@ModelAttribute("user") @Valid User user,
                                   BindingResult userBindingResult,
                                   @RequestParam(value = "roles", required = false) @Valid List<String> roles,
                                   BindingResult rolesBindingResult,
                                   RedirectAttributes redirectAttributes) {

        userValidator.validate(user, userBindingResult);
        if (userBindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorsUser", userBindingResult.getAllErrors());
            return "/admin/userUpdate";
        }

        roleValidator.validate(roles, rolesBindingResult);
        if (rolesBindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorsRoles", rolesBindingResult.getAllErrors());
            return "/admin/userUpdate";
        }

        userService.editUser(user, roles);
        return "redirect:/admin/users";
    }

    @GetMapping("registration")
    public String registrationPageEndPoint(@ModelAttribute("user") User user) {
        return "admin/registration";
    }

    @PostMapping("/registration")
    public String performRegistrationEndPoint(@ModelAttribute("user") @Valid User user,
                                              BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/registration";
        }
        userService.registerUser(user);

        return "redirect:/admin/users";
    }
}
