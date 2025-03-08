package com.example.security.spring_security.service;

import com.example.security.spring_security.model.Role;
import com.example.security.spring_security.model.User;
import com.example.security.spring_security.repositories.RoleRepository;
import com.example.security.spring_security.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    @Lazy
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(long id, User updateuser) {
        User userFromBase = findById(id);
        //userFromBase.setUserName(updateuser.getUserName());
        userFromBase.setEmail(updateuser.getEmail());
        userFromBase.setPassword(bCryptPasswordEncoder.encode(updateuser.getPassword()));
        userFromBase.setFirstName(updateuser.getFirstName());
        userFromBase.setLastName(updateuser.getLastName());
        userFromBase.setAge(updateuser.getAge());
        userFromBase.setId(updateuser.getId());
        if (updateuser.getRoles() != null) {
            userFromBase.getRoles().clear(); // Очищаем текущие роли
            userFromBase.getRoles().addAll(updateuser.getRoles()); // Добавляем новые роли
        }
        userRepository.save(userFromBase);
    }

    @Override
    @Transactional
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName).get();
    }


    }

