package drivus.dto;

import drivus.model.EFuelType;

public record CreateVehicleResponse(
        Long id,
        String brand,
        String model,
        Integer year,
        EFuelType fuelType) {
}
