package kg.kim.TaskManagementAPI.payload.user;

public record UserSignInResponse(
        String jwt,
        String refresh_token
) {
}
