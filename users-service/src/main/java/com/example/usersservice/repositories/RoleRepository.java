package com.example.usersservice.repositories;

import com.example.usersservice.models.ERole;
import com.example.usersservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository< Role , Long> {
    Optional<Role> findByName(ERole roleAdmin);
}
