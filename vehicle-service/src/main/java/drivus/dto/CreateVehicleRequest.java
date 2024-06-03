package drivus.dto;

public record CreateVehicleRequest(
        String brand,
        Long userId,
        String model,
        Integer year,
        String fuelType) {
}
