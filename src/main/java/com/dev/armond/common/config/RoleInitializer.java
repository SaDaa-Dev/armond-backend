package com.dev.armond.common.config;

import com.dev.armond.member.entity.Role;
import com.dev.armond.member.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RoleInitializer {
    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                roleRepository.save(new Role("ROLE_USER"));
                roleRepository.save(new Role("ROLE_ADMIN"));
            }
            log.info("DEFAULT ROLES INSERT");
        };
    }
}
