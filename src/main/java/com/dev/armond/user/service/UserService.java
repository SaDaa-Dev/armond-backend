package com.dev.armond.user.service;

import com.dev.armond.user.dto.SignUpRequest;
import com.dev.armond.user.entity.User;

public interface UserService {
    User registerUser(SignUpRequest signUpRequest);
}
