package com.example.security.spring_security.repositories;

import com.example.security.spring_security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
