package com.example.security.spring_security.repositories;

import com.example.security.spring_security.model.Role;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(@NotEmpty String roleName);
}
