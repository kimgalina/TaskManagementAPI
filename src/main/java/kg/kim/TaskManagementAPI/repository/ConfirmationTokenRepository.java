package kg.kim.TaskManagementAPI.repository;

import kg.kim.TaskManagementAPI.entity.ConfirmationToken;
import kg.kim.TaskManagementAPI.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByUser(User user);

    Optional<ConfirmationToken> findByToken(String token);
}
