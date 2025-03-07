package com.example.security.spring_security.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "users")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "userName", unique = true)
    @NotEmpty(message = "Enter a name")
    private String userName;

    @Column(name = "email")
    @NotEmpty(message = "Enter email")
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "Enter password")
    private String password;
    @Column(name = "age")
    private int age;

    @Column(name = "lastName")
    private String lastName;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "usersRole",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles;

    //String nameForRole;

    public User() {
    }

    public User(String firstName, String email, String password, Set<Role> roles, int age, String lastName) {
        this.userName = firstName;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.age = age;
        this.lastName = lastName;


    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String firstName) {
        this.userName = firstName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}