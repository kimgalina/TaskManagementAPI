package kg.kim.TaskManagementAPI.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import kg.kim.TaskManagementAPI.entity.ConfirmationToken;
import kg.kim.TaskManagementAPI.entity.RefreshToken;
import kg.kim.TaskManagementAPI.entity.User;
import kg.kim.TaskManagementAPI.enums.Role;
import kg.kim.TaskManagementAPI.enums.UserStatus;
import kg.kim.TaskManagementAPI.exception.NotFoundException;
import kg.kim.TaskManagementAPI.exception.NotValidException;
import kg.kim.TaskManagementAPI.mapper.UserMapper;
import kg.kim.TaskManagementAPI.payload.token.RefreshTokenRequest;
import kg.kim.TaskManagementAPI.payload.user.UserCreateRequest;
import kg.kim.TaskManagementAPI.payload.user.UserCreateResponse;
import kg.kim.TaskManagementAPI.payload.user.UserSignInRequest;
import kg.kim.TaskManagementAPI.payload.user.UserSignInResponse;
import kg.kim.TaskManagementAPI.repository.ConfirmationTokenRepository;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

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
    private final EmailService emailService;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public ResponseEntity<UserCreateResponse> createUser(UserCreateRequest user, HttpServletRequest servletRequest) {
        if(!user.password().equals(user.confirmPassword())) {
            throw new NotValidException("Your password and confirm password not equals");
        }
        if(userRepository.findByEmail(user.email()).isPresent()) {
            throw new NotValidException("User with that email address is already exist");
        }
        User userEntity = mapper.toEntity(user);
        userEntity.setRole(Role.ROLE_USER);
        userEntity.setUserTasks(Collections.emptyList());
        userEntity.setUserProjects(Collections.emptyList());
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setStatus(UserStatus.NOT_VERIFIED);
        userRepository.save(userEntity);

        sendConfirmationToken(userEntity, servletRequest);
        return new ResponseEntity<>(mapper.toUserCreateResponse(userEntity), HttpStatus.OK);
    }

    public void sendConfirmationToken(User user, HttpServletRequest request) {
        ConfirmationToken token = createConfirmationToken(user);
        String confirmationUrl = getConfirmationUrl(request, token.getToken());
        System.out.println("Сcылка на подтверждение " + confirmationUrl);
        emailService.sendConfirmationEmail(token, confirmationUrl);
    }

    private ConfirmationToken createConfirmationToken(User user) {
        String randomString = UUID.randomUUID().toString();

        Optional<ConfirmationToken> token = confirmationTokenRepository.findByUser(user);
        if (token.isPresent()) {
            token.get().setToken(randomString);
            token.get().setDates(LocalDateTime.now());
            return confirmationTokenRepository.save(token.get());
        } else {
            ConfirmationToken newToken = new ConfirmationToken(randomString, user);
            return confirmationTokenRepository.save(newToken);
        }
    }

    private String getConfirmationUrl(HttpServletRequest servletRequest, String token) {
        return "http://" + servletRequest.getServerName() + ":"
                + servletRequest.getServerPort() + "/api/auth/verify?token=" + token;
    }

    private boolean isTokenExpired(ConfirmationToken token) {
        LocalDateTime currentDate = LocalDateTime.now();
        return currentDate.isAfter(token.getExpiredAt());
    }

    public ResponseEntity<String> checkUserVerification(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException("Такого токена не существует"));

        if (isTokenExpired(confirmationToken)) {
            throw new NotValidException("Ссылка для подтверждения аккаунта истекла");
        }
        User userEntity = confirmationToken.getUser();
        userEntity.setStatus(UserStatus.VERIFIED);
        userRepository.save(userEntity);
        return ResponseEntity.ok("<h1>Вы подтвердили свой аккаунт = ) <h1>");
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
