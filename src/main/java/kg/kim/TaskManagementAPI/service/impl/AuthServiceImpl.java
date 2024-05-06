package kg.kim.TaskManagementAPI.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import kg.kim.TaskManagementAPI.entity.RefreshToken;
import kg.kim.TaskManagementAPI.entity.User;
import kg.kim.TaskManagementAPI.exception.NotFoundException;
import kg.kim.TaskManagementAPI.exception.NotValidException;
import kg.kim.TaskManagementAPI.mapper.UserMapper;
import kg.kim.TaskManagementAPI.payload.token.RefreshTokenRequest;
import kg.kim.TaskManagementAPI.payload.user.UserCreateRequest;
import kg.kim.TaskManagementAPI.payload.user.UserCreateResponse;
import kg.kim.TaskManagementAPI.payload.user.UserSignInRequest;
import kg.kim.TaskManagementAPI.payload.user.UserSignInResponse;
import kg.kim.TaskManagementAPI.repository.UserRepository;
import kg.kim.TaskManagementAPI.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper mapper;
    private final RefreshTokenService refreshTokenService;

    @Override
    public ResponseEntity<UserCreateResponse> createUser(UserCreateRequest user, HttpServletRequest servletRequest) {
        return null;
    }

    @Override
    public ResponseEntity<UserSignInResponse> authenticate(UserSignInRequest userRequest) {

        User userEntity = userRepository.findByEmail(userRequest.email())
                .orElseThrow(() -> new NotFoundException("Пользователь с такой почтой не найден"));

        if (!passwordEncoder.matches(userRequest.password(), userEntity.getPassword())) {
            throw new NotValidException("Вы ввели неверный пароль");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.email(),
                        userRequest.password()
                )
        );

        String jwtToken = jwtService.generateToken(userEntity);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userRequest.email());
        UserSignInResponse response = new UserSignInResponse(jwtToken, refreshToken.getToken());

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<UserSignInResponse> refreshToken(RefreshTokenRequest refreshToken) {
        return refreshTokenService.findByToken(refreshToken.refreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user);
                    return new ResponseEntity(new UserSignInResponse(accessToken, refreshToken.refreshToken()), HttpStatus.OK);
                }).orElseThrow(() -> new RuntimeException("Refresh Token is not in DB..!!"));
    }
}
