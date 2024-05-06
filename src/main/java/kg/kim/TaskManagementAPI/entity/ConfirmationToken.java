package kg.kim.TaskManagementAPI.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class ConfirmationToken extends Token{
    public ConfirmationToken(String token, User user) {
        super(token, user);
    }
}
