package kg.kim.TaskManagementAPI.service.impl;

import kg.kim.TaskManagementAPI.entity.RefreshToken;
import kg.kim.TaskManagementAPI.entity.User;
import kg.kim.TaskManagementAPI.exception.NotFoundException;
import kg.kim.TaskManagementAPI.repository.RefreshTokenRepository;
import kg.kim.TaskManagementAPI.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    public RefreshToken createRefreshToken(String username){
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException("user with username " + username + " not found"));
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), user);
        return refreshTokenRepository.save(refreshToken);
    }



    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiredAt().compareTo(LocalDateTime.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }

}
