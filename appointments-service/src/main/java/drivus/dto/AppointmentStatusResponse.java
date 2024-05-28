package drivus.dto;

import java.time.LocalDateTime;

public record AppointmentStatusResponse(
        Long id,
        Long appointmentId,
        String status,
        LocalDateTime createdAt) {
}