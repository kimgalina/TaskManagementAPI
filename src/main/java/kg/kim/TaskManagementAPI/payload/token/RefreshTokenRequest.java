package kg.kim.TaskManagementAPI.payload.token;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RefreshTokenRequest(
        @JsonProperty("refresh_token")
        String refreshToken
) {
}
