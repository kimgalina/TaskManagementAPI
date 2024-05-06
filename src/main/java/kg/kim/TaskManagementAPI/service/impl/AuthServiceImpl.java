package kg.kim.TaskManagementAPI.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import kg.kim.TaskManagementAPI.entity.User;
import kg.kim.TaskManagementAPI.exception.NotFoundException;
import kg.kim.TaskManagementAPI.exception.NotValidException;
import kg.kim.TaskManagementAPI.mapper.UserMapper;
import kg.kim.TaskManagementAPI.payload.user.UserCreateRequest;
import kg.kim.TaskManagementAPI.payload.user.UserCreateResponse;
import kg.kim.TaskManagementAPI.payload.user.UserSignInRequest;
import kg.kim.TaskManagementAPI.payload.user.UserSignInResponse;
import kg.kim.TaskManagementAPI.repository.UserRepository;
import kg.kim.TaskManagementAPI.service.AuthService;
import lombok.AllArgsConstructor;
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
        UserSignInResponse response = new UserSignInResponse(jwtToken);

        return ResponseEntity.ok(response);
    }
}
