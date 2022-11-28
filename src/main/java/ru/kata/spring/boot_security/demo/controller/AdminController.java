package ru.kata.spring.boot_security.demo.controller;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.List;


@RestController
public class AdminController {

    private final UserService userService;
    private final UserRepository userRepository;

    public AdminController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/admin-all")
    private List<User> allUsers() {
        return userService.allUsers();

    }
    @GetMapping("/admin-findId/{id}")
    private User findUserById(@PathVariable("id")Long id){
        return userService.findUserById(id);
    }

    @PostMapping("/admin-new")
    public void create(@RequestBody User user)
    {
        userService.saveUser(user);
    }

    @PatchMapping("admin-update/{id}")
    public User update(@RequestBody User user, @PathVariable("id") Long id) {
        System.out.println(user);
        userService.update(id, user);
        return user;
    }

    @DeleteMapping("admin/{id}")
    public void delete(@PathVariable("id") Long id) {
       userService.deleteUser(id);
    }

}
