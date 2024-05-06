package kg.kim.TaskManagementAPI.entity;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
public class RefreshToken extends Token {
    public RefreshToken(String token, User user) {
        super(token, user);
    }

}
