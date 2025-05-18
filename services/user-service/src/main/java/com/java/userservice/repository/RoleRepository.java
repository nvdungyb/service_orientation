package com.java.userservice.repository;

import com.java.userservice.domain.Role;
import com.java.userservice.enums.Erole;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findRoleByErole(Erole erole);
}
