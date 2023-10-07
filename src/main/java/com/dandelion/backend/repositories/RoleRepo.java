package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.Role;
import com.dandelion.backend.entities.enumType.RoleBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(RoleBase role);

    List<Role> findByRoleNameIn(Collection<RoleBase> roleNames);


}
