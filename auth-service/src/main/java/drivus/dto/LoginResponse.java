package drivus.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
        @Schema(description = "id")
        Long id,
        @Schema(description = "username")
        String username,
        @Schema(description = "email")
        String email) {
}
