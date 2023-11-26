package com.dandelion.backend;

import com.dandelion.backend.entities.Address;
import com.dandelion.backend.entities.Role;
import com.dandelion.backend.entities.User;
import com.dandelion.backend.entities.enumType.RoleBase;
import com.dandelion.backend.repositories.AddressRepo;
import com.dandelion.backend.repositories.RoleRepo;
import com.dandelion.backend.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@RequiredArgsConstructor
public class BackendApplication implements CommandLineRunner {

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final AddressRepo addressRepo;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);

    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        createRoleIfNotExists(RoleBase.ADMIN);
        createRoleIfNotExists(RoleBase.MANAGER);
        createRoleIfNotExists(RoleBase.CUSTOMER);

        createUserIfNotExists("Phat Ly Admin", "admin@gmail.com", RoleBase.ADMIN, RoleBase.MANAGER, RoleBase.CUSTOMER);
        createUserIfNotExists("Xiao Jiu Manager", "manager@gmail.com", RoleBase.MANAGER, RoleBase.CUSTOMER);
        createUserIfNotExists("John Customer", "customer@gmail.com", RoleBase.CUSTOMER);
    }

    private void createUserIfNotExists(String fullName, String email, RoleBase... roleBases) {
        if (!userRepo.existsByEmailIgnoreCase(email)) {

            User user = new User();
            user.setFullName(fullName);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("password"));
            user.setRoles(getRoles(roleBases));
            user.setFullName(fullName);
            user.setPhone("");
            userRepo.save(user);

            Address address = new Address();
            address.setAddressLine1("137/24 Mau Than street, Ninh Kieu district, Can Tho city");
            address.setUser(user);
            address.setIsDefault(true);
            addressRepo.save(address);
        }
    }

    private void createRoleIfNotExists(RoleBase roleName) {
        if (!roleRepo.existsByRoleName(roleName)) {
            Role role = new Role();
            role.setRoleName(roleName);
            roleRepo.save(role);
        }
    }

    private List<Role> getRoles(RoleBase... roleBases) {
        return Arrays.stream(roleBases)
                .map(roleRepo::findByRoleName)
                .collect(Collectors.toList());
    }
}