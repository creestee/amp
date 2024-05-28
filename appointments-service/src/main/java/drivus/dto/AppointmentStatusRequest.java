package drivus.dto;

public record AppointmentStatusRequest(
        Long appointmentId,
        String status) {
}