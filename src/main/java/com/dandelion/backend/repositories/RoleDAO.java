package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.Role;
import com.dandelion.backend.entities.enumType.RoleBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDAO extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(RoleBase role);

}
