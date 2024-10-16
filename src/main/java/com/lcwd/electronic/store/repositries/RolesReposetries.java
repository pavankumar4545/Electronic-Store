package com.lcwd.electronic.store.repositries;

import com.lcwd.electronic.store.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesReposetries extends JpaRepository<Roles,String> {

    public Optional<Roles> findByRoleName(String roleName);

}
