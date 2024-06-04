package drivus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class SpDTO extends JdkSerializationRedisSerializer implements Serializable {
    private Long id;
    private String name;
    private String address;
    private String about;
    private List<String> services;

    private static final long serialVersionUID = -64702433709029707L;
}
