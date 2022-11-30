package ru.kata.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.security.Principal;


@RestController
public class UserRESTController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserRESTController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/user-info")
    public User show(Principal principal) {
        return userRepository.findByUsername(principal.getName());
    }
}
