package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class AdminController {
    private final UserService userService;

    private final UserRepository userRepository;

    public AdminController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/admin")
    public String index(Model model) {
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("newUser", new User());
        return ("admin");
    }

    @GetMapping("/admin/")
    public String getUserRole(@ModelAttribute("user") User user, Model model, Principal principal) {
        model.addAttribute("getRole",userService.getUserRole(user));
        return ("admin");
    }

    @PostMapping("/admin-new")
    public String create(@ModelAttribute("user") User user,Model model) {
        model.addAttribute("newUser", new User());
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping ("admin-update/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("admin/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
