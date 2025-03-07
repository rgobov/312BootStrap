package com.example.security.spring_security.service;




import com.example.security.spring_security.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(long id);

    void save(User user);

    void update(long id, User updateuser);

    void delete(long id);

    User findByUserName(String userName);
}
