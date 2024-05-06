package kg.kim.TaskManagementAPI.payload.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import kg.kim.TaskManagementAPI.enums.Role;

public record UserResponse(
        Long id,
        String email,

        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String LastName,

        String phone,
        Role role
) {
}
