package com.example.security.spring_security.repositories;

import com.example.security.spring_security.model.User;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"roles"}) // Добавляем EntityGraph для загрузки ролей

    Optional<User> findById(Long id);

    Optional<User> existsByEmail(@NotEmpty(message = "Enter email") String email);
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByEmail(String email);


}
