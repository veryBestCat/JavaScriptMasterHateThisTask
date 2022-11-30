package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceIn {

    final UserRepository userRepository;
    final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public User findUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(new User());
    }

    public Set<Role> getUserRoles(User user) {
        Set<Role> userNew = user.getRoles();
        return userNew;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public boolean saveUser(User user) {
        User userNew = userRepository.findByUsername(user.getUsername());
        if (userNew != null) {
            System.out.println("Пользователь с таким именем уже существует!");
            return false;
        } else {
            user.setRoles(user.getRoles());
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user.setLevel(user.getLevel());
            userRepository.save(user);
            System.out.println("Пользователь " + user + " сохранен");
            return true;
        }
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
        System.out.println("Пользователь " + userId + " удален");

    }

    @Transactional
    @Override
    public void updateUser(Long id, User userUpDate) {
        User user = findUserById(id);
        user.setUsername(userUpDate.getUsername());
        user.setLevel(userUpDate.getLevel());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(userUpDate);
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new org.springframework.security.core.userdetails.User(findByUserName(username).getUsername(), findByUserName(username).getPassword(), findByUserName(username).getRoles());
    }

}
