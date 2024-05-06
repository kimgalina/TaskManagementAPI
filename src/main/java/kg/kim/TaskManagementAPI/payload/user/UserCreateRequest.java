package kg.kim.TaskManagementAPI.payload.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
        @JsonProperty("first_name")
        @NotBlank(message = "Имя - обязательное поле ")
        @Size(min = 3, max = 50, message = "First name should be between 3 and 50 characters")
        String firstName,
        @JsonProperty("last_name")
        @NotBlank(message = "Фамилия - обязательное поле ")
        @Size(min = 3, max = 50, message = "Last name should be between 3 and 50 characters")
        String lastName,
        @NotBlank(message = "Укажите ваш email адрес")
        @Size(max = 150, message = "Email should be less than 150 characters")
        @Email(message = "Enter valid email address")
        String email,
        @NotBlank(message = "Укажите номер телефона")
        @Size(min = 10, max = 30, message = "Phone should be between 10 and 30characters")
        @Pattern(regexp = "^\\+?[0-9]+$", message = "Phone number should have only numbers")
        String phone,

       @NotBlank(message = "Укажите ваш пароль")
       @Size(max = 150, message = "Password should be less than 150 characters")
       String password,

       @NotBlank(message = "Введите пароль повторно")
       @Size(max = 150, message = "ConfirmPassword should be less than 150 characters")
       String confirmPassword

) {
}
