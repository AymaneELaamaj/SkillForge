package com.skillforge.identity.security.config;

import com.skillforge.identity.entity.Role;
import com.skillforge.identity.enums.RoleName;
import com.skillforge.identity.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            createRoleIfMissing(roleRepository, RoleName.ROLE_USER);
            createRoleIfMissing(roleRepository, RoleName.ROLE_ADMIN);
        };
    }

    private void createRoleIfMissing(RoleRepository roleRepository, RoleName roleName) {
        roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }
}
