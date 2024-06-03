package drivus.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppointmentStatusResponse extends JdkSerializationRedisSerializer implements Serializable {
    private Long id;
    private Long appointmentId;
    private String status;
    private LocalDateTime createdAt;
    private String notes;

    private static final long serialVersionUID = -64702433709029707L;

    public AppointmentStatusResponse(Long id, Long appointmentId, String status, LocalDateTime createdAt, String notes) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.status = status;
        this.createdAt = createdAt;
        this.notes = notes;
    }
}