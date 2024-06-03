package drivus.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointment_statuses", schema = "drivus")
@Data
public class AppointmentStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "appointmentId", nullable = false)
    private Long appointmentId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "notes", nullable = false)
    private String notes;
}