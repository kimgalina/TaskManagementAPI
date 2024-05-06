package kg.kim.TaskManagementAPI.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import kg.kim.TaskManagementAPI.payload.user.UserCreateRequest;
import kg.kim.TaskManagementAPI.payload.user.UserCreateResponse;
import kg.kim.TaskManagementAPI.payload.user.UserSignInRequest;
import kg.kim.TaskManagementAPI.payload.user.UserSignInResponse;
import kg.kim.TaskManagementAPI.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public ResponseEntity<UserCreateResponse> createUser(UserCreateRequest user, HttpServletRequest servletRequest) {
        return null;
    }

    @Override
    public ResponseEntity<UserSignInResponse> authenticate(UserSignInRequest userRequest) {
        return null;
    }
}
