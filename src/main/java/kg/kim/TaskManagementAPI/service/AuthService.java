package kg.kim.TaskManagementAPI.service;

import jakarta.servlet.http.HttpServletRequest;
import kg.kim.TaskManagementAPI.payload.token.RefreshTokenRequest;
import kg.kim.TaskManagementAPI.payload.user.UserCreateRequest;
import kg.kim.TaskManagementAPI.payload.user.UserCreateResponse;
import kg.kim.TaskManagementAPI.payload.user.UserSignInRequest;
import kg.kim.TaskManagementAPI.payload.user.UserSignInResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<UserCreateResponse> createUser(UserCreateRequest user, HttpServletRequest servletRequest);

    ResponseEntity<UserSignInResponse> authenticate(UserSignInRequest userRequest);
    ResponseEntity<UserSignInResponse> refreshToken(RefreshTokenRequest refreshToken);

}
