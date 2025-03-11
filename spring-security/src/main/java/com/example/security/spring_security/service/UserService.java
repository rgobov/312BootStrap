package com.example.security.spring_security.service;




import com.example.security.spring_security.model.Role;
import com.example.security.spring_security.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> findAll();
    User findById(long id);
    void save(User user);
    void update(long id, User updateuser);
    void delete(long id);
    void setRolesForUser(User user, Set<Long> roleIds);
    UserDetails getUserDetails();
    User findByEmail(String email);
    List<Role> getAllRoles(); // Новый метод для получения всех ролей
}