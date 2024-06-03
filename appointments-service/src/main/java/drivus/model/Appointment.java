package drivus.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments", schema = "drivus")
@Data
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "isOngoing", nullable = false)
    private Boolean isOngoing;

    @Column(name = "vehicleId", nullable = false)
    private Long vehicleId;

    @Column(name = "serviceProviderId", nullable = false)
    private Long serviceProviderId;

    @Column(name = "appointmentDate", nullable = false)
    private LocalDateTime appointmentDate;

    @Column(name = "serviceType", nullable = false)
    private String serviceType;

    @Column(name = "notes")
    private String notes;
}