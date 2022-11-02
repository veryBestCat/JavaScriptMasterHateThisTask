package ru.kata.spring.boot_security.demo.model;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, length = 20)
    @Size(min=3, message = "Не меньше 3ёх знаков")
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "level", nullable = false, length = 20)
    @Size(min=4, message = "Не меньше 4 знаков")
    private String level;

    @Column(name = "password",nullable = false, length = 150)
    @Size(min=4, message = "Не меньше 4 знаков") //@NotBlank аннотация не пропускающая пустоты
    private String password;

    @Transient
    private String passwordConfirm;

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + username + '\'' +
                ", level='" + level + '\'' +
                '}';
    }

    public User(Long id, String username, String level) {
        this.id = id;
        this.username = username;
        this.level = level;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // Возвращаем список ролей пользователя
        return getRoles();
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() { //аккаунт не просрочен
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {// аккаунт не заблокирован
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // пароль не просрочен
        return true;
    }

    @Override
    public boolean isEnabled() { // аккаунт активен/включен/работает
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && username.equals(user.username) && level.equals(user.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, level);
    }
}
