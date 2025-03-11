package com.example.security.spring_security.service;

import com.example.security.spring_security.model.Role;
import com.example.security.spring_security.model.User;
import com.example.security.spring_security.repositories.RoleRepository;
import com.example.security.spring_security.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;


    @Lazy
    BCryptPasswordEncoder bCryptPasswordEncoder;

    RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;

    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
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
        userFromBase.setFirstName(updateuser.getFirstName());
        userFromBase.setLastName(updateuser.getLastName());
        userFromBase.setAge(updateuser.getAge());
        userFromBase.setId(updateuser.getId());
        if (updateuser.getRoles() != null) {
            userFromBase.getRoles().clear(); // Очищаем текущие роли
            userFromBase.getRoles().addAll(updateuser.getRoles()); // Добавляем новые роли
        }
        if (updateuser.getPassword() == null || updateuser.getPassword().isEmpty()) {
            userFromBase.setPassword(userFromBase.getPassword());
        }else userFromBase.setPassword(bCryptPasswordEncoder.encode(updateuser.getPassword()));
        userRepository.save(userFromBase);
    }

    @Override
    @Transactional
    public void delete(long id) {
        userRepository.deleteById(id);
    }


    public void setRolesForUser(User user, Set<Long> roleIds) {
        Set<Role> roles = roleIds.stream()
                .map(id -> roleRepository.findById(Long.valueOf(id))) // Ищем роли по ID
                .filter(Optional::isPresent) // Отфильтровываем пустые Optional
                .map(Optional::get) // Извлекаем Role из Optional
                .collect(Collectors.toSet());
        user.setRoles(roles); // Устанавливаем роли для пользователя
    }

    @Override
    public UserDetails getUserDetails() {
         return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new UsernameNotFoundException("Пользователь с email " + email + " не найден"));
    }


    Long getUserId(Long userId) {
        return userRepository.findById(userId).get().getId();
    }

}

