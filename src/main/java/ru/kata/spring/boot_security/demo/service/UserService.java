package ru.kata.spring.boot_security.demo.service;

import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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


    @Override
    public List<User> allUsers() {
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
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
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
        userRepository.findById(userId);
        userRepository.deleteById(userId);
        System.out.println("Пользователь " + userId + " удален");

    }

    @Transactional
    @Override
    public void update(Long id, User userUpDate) {
        User user = findUserById(id);
        user.setUsername(userUpDate.getUsername());
        user.setLevel(userUpDate.getLevel());
        user.setPassword(new BCryptPasswordEncoder().encode(userUpDate.getPassword()));
        user.setRoles(userUpDate.getRoles());
        userRepository.save(user);
    }


    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRoleToAuthorities(user.getRoles()));
    }

    @Transactional
    @Override
    public Collection<? extends GrantedAuthority> mapRoleToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
