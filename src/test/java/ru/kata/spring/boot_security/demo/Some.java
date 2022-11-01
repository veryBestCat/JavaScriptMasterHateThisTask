package ru.kata.spring.boot_security.demo;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Some {
    @Test
    public void Enigma() {
        System.out.println(new BCryptPasswordEncoder(5).encode("user"));
    }
}
