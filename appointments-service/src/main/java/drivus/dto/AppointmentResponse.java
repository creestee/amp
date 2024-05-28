package drivus.dto;

import java.time.LocalDateTime;

public record AppointmentResponse(
        Long id,
        Long userId,
        Long vehicleId,
        Long serviceProviderId,
        LocalDateTime appointmentDate,
        String serviceType,
        String notes) {
}