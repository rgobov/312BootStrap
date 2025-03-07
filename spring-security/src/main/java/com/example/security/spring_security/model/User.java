package com.example.security.spring_security.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "firstName")
    @NotEmpty(message = "Enter a name")
    private String firstName;

    @Column(name = "email")
    @NotEmpty(message = "Enter email")
    private String email;

    @Column(name = "userName")
    private String userName;

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
    private Set<Role> roles;

    // Default constructor
    public User() {
    }

    // Parameterized constructor
    public User(String firstName, String email, String password, Set<Role> roles, int age, String lastName) {
        this.firstName = firstName;
        this.email = email;
        this.userName = email; // Set userName to email
        this.password = password;
        this.roles = roles;
        this.age = age;
        this.lastName = lastName;
    }

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.userName = email; // Synchronize userName with email
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        // Do not allow direct modification of userName
        // It should always be derived from email
        throw new UnsupportedOperationException("UserName is derived from email and cannot be set directly.");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}