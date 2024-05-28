package drivus.dto;

import java.time.LocalDateTime;

public record AppointmentRequest(
        Long userId,
        Long vehicleId,
        Long serviceProviderId,
        LocalDateTime appointmentDate,
        String serviceType,
        String notes) {
}