package kg.kim.TaskManagementAPI.entity;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class ConfirmationToken extends Token{
    public ConfirmationToken(String token, User user) {
        super(token, user);
    }
}
