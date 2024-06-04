package drivus.dto;

import java.util.List;

public record CreateSpRequest(
        String name,
        String address,
        String about,
        List<String> services) {
}
