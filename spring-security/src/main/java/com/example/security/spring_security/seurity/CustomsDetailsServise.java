package com.example.security.spring_security.seurity;

import com.example.security.spring_security.model.PersonDetails;
import com.example.security.spring_security.model.User;
import com.example.security.spring_security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomsDetailsServise implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public void SetCustomsDetailsServise(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         Optional<User> user = userRepository.findByUserName(username);
         if (user.isEmpty()) {
             throw new UsernameNotFoundException(username);
         }
         return new PersonDetails(user.get());


    }


}


