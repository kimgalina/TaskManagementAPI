package kg.kim.TaskManagementAPI.payload.user;

import kg.kim.TaskManagementAPI.enums.Role;
import kg.kim.TaskManagementAPI.enums.UserStatus;

public record UserCreateResponse(
        Long id,

        String email,

        String password,

        String firstName,

        String lastName,

        String phone,

        Role role,

        UserStatus status
) {
}
