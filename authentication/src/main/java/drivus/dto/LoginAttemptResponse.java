package drivus.dto;

import drivus.model.LoginAttempt;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record LoginAttemptResponse(
        @Schema(description = "The date and time of the login attempt") LocalDateTime createdAt,
        @Schema(description = "The login status") boolean success) {

    public static LoginAttemptResponse convertToDTO(LoginAttempt loginAttempt) {
        return new LoginAttemptResponse(loginAttempt.getCreatedAt(), loginAttempt.isSuccess());
    }
}

