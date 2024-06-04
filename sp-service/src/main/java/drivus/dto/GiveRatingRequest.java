package drivus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GiveRatingRequest {
    private Long userId;
    private Integer rating;
}
