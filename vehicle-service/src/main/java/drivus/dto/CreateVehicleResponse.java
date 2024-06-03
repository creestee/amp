package drivus.dto;

import drivus.model.EFuelType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class CreateVehicleResponse extends JdkSerializationRedisSerializer implements Serializable {
    private Long id;
    private Long userId;
    private String brand;
    private String model;
    private Integer year;
    private EFuelType fuelType;

    private static final long serialVersionUID = -64702433709029707L;
}