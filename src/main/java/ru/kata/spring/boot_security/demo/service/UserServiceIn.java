package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.Collection;
import java.util.List;


public interface UserServiceIn extends UserDetailsService {

    public User findByUserName(String username);

    public User findUserById(Long userId);

    public List<User> allUsers();

    public boolean saveUser(User user);

    public void deleteUser(Long userId);

    public void update(Long id, User userUpDate);

    public Collection<? extends GrantedAuthority> mapRoleToAuthorities(Collection<Role> roles);

}
