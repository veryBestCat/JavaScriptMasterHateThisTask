package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    final UserRepository userRepository;
    final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User findUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        if (user.getUsername() != null) {
            System.out.println("Пользователь с таким именем уже существует!");
            return false;
        } else {
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
            user.setPassword(user.getPassword());
            userRepository.save(user);
            System.out.println("Пользователь " + user + " сохранен");
            return true;
        }
    }

    public void deleteUser(Long userId) {
        userRepository.findById(userId);
        userRepository.deleteById(userId);
        System.out.println("Пользователь " + userId + " удален");

    }

    public void update(Long id, User userUpDate) {
        User user = findUserById(id);
        user.setUsername(userUpDate.getUsername());
        user.setLevel(userUpDate.getLevel());
        user.setPassword(userUpDate.getPassword());
        user.setRoles(userUpDate.getRoles());
        userRepository.save(user);
    }
}