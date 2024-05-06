package kg.kim.TaskManagementAPI.payload.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @NotBlank(message = "Заполните ваш новый email или напишите старый")
        @Size(min=10, max = 255, message = "Ваш Email должен быть от 10 до 255 символов")
        @Email(message = "Укажите верный адрес")
        String email,

        @JsonProperty("first_name")
        @NotBlank(message = "Напишите имя")
        @Size(min=10, max = 255, message = "Ваше имя должно быть от 10 до 255 символов")
        String firstName,
        @NotBlank(message = "Напишите фамилию")
        @Size(min=10, max = 255, message = "Ваш фамилия должна быть от 10 до 255 символов")
        @JsonProperty("last_name")
        String lastName,
        @NotBlank(message = "Напишите номер телефона")
        @Size(min=10, max = 255, message = "Ваш номер должен быть от 10 до 255 символов")
        @Pattern(regexp = "^\\+?[0-9]+$", message = "Номер телефона должен включать только цифры")
        String phone
) {
}
