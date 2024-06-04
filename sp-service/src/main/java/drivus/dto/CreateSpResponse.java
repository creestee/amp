package drivus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class CreateSpResponse extends JdkSerializationRedisSerializer implements Serializable {
    private Long id;
    private String name;
    private String address;
    private String about;
    private List<String> services;

    private static final long serialVersionUID = -64702433709029707L;
}