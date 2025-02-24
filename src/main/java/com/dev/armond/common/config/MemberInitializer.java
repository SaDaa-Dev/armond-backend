package com.dev.armond.common.config;

import com.dev.armond.member.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "local"})
public class MemberInitializer {
    @Bean
    public CommandLineRunner initMemberForTest(MemberRepository memberRepository) {
        return args -> {

        };
    }
}
