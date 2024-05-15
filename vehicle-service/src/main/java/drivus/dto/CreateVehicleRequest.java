package drivus.dto;

public record CreateVehicleRequest(
        String brand,
        String model,
        Integer year,
        String fuelType) {
}
