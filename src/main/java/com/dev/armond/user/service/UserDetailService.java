package com.dev.armond.user.service;

import com.dev.armond.user.dto.CustomUserDetails;

public interface UserDetailService {
    CustomUserDetails loadUserByUsername(String userEmail);
}
