package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.Role;
import com.dandelion.backend.entities.enumType.RoleBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {

    Role findByRoleName(RoleBase role);

    boolean existsByRoleName(RoleBase roleName);

    
}
