package com.dev.armond.user.service.impl;

import com.dev.armond.user.dto.SignUpRequest;
import com.dev.armond.user.entity.Role;
import com.dev.armond.user.entity.User;
import com.dev.armond.user.repository.RoleRepository;
import com.dev.armond.user.repository.UserRepository;
import com.dev.armond.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(SignUpRequest request) {
        if(userRepository.existsByEmail(request.email())){
            throw new RuntimeException("이미 등록된 이메일입니다.");
        }

        if (userRepository.existsByNickName(request.nickName())) {
            throw new RuntimeException("이미 존재하는 닉네임입니다.");
        }

        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("기본 권한이 없습니다."));

        String encodedPassword = passwordEncoder.encode(request.password());

        User user = User.builder()
                .name(request.name())
                .nickName(request.nickName())
                .password(encodedPassword)
                .email(request.email())
                .gender(request.gender())
                .height(request.height())
                .weight(request.weight())
                .goalCalories(request.goalCalories())
                .roles(Set.of(defaultRole))
                .build();

        return userRepository.save(user);
    }
}
