package com.dev.armond.user.service.impl;

import com.dev.armond.user.dto.CustomUserDetails;
import com.dev.armond.user.entity.User;
import com.dev.armond.user.repository.UserRepository;
import com.dev.armond.user.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailService {
    private final UserRepository userRepository;
    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 사용자 정보를 바탕으로 CustomUserDetails 객체를 반환
        return new CustomUserDetails(user.getEmail(), user.getPassword(), true, user.getAuthorities());
    }
}
