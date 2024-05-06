package kg.kim.TaskManagementAPI.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.kim.TaskManagementAPI.payload.token.RefreshTokenRequest;
import kg.kim.TaskManagementAPI.payload.user.UserCreateRequest;
import kg.kim.TaskManagementAPI.payload.user.UserCreateResponse;
import kg.kim.TaskManagementAPI.payload.user.UserSignInRequest;
import kg.kim.TaskManagementAPI.payload.user.UserSignInResponse;
import kg.kim.TaskManagementAPI.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserCreateResponse> signUp(@Valid @RequestBody UserCreateRequest userRequest,
                                                     HttpServletRequest servletRequest) {
        return authService.createUser(userRequest, servletRequest);
    }



    @PostMapping("/sign-in")
    public ResponseEntity<UserSignInResponse> signIn(@Valid @RequestBody UserSignInRequest userRequest) {
        return authService.authenticate(userRequest);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<UserSignInResponse> refreshTokens(@RequestBody RefreshTokenRequest refreshTokenRequestDTO) {
        return authService.refreshToken(refreshTokenRequestDTO);
    }
}
