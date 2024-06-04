package drivus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class RatingResponse extends JdkSerializationRedisSerializer implements Serializable {
    private Long serviceProviderId;
    private Double rating;

    private static final long serialVersionUID = -64702433709029707L;
}
