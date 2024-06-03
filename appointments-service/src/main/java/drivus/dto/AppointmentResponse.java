package drivus.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppointmentResponse extends JdkSerializationRedisSerializer implements Serializable {
    private Long id;
    private Long userId;
    private Long vehicleId;
    private Long serviceProviderId;
    private LocalDateTime appointmentDate;
    private String serviceType;
    private String notes;
    private Boolean isOngoing;

    private static final long serialVersionUID = -64702433709029707L;

    public AppointmentResponse(Long id, Long userId, Long vehicleId, Long serviceProviderId, LocalDateTime appointmentDate, String serviceType, String notes, Boolean isOngoing) {
        this.id = id;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.serviceProviderId = serviceProviderId;
        this.appointmentDate = appointmentDate;
        this.serviceType = serviceType;
        this.notes = notes;
        this.isOngoing = isOngoing;
    }
}