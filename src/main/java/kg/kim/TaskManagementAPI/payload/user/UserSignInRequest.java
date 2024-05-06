package kg.kim.TaskManagementAPI.payload.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserSignInRequest(
        @NotBlank
        @Size(max = 150, message = "Email should be less than 150 characters")
        @Email(message = "Enter valid email address")
        String email,
        @NotBlank
        @Size(max = 150, message = "Password should be less than 150 characters")
        String password
) {
}
