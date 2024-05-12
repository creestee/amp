package drivus.model;

import lombok.Getter;

@Getter
public enum EFuelType {
    PETROL("Petrol"),
    DIESEL("Diesel"),
    ELECTRIC("Electric"),
    HYBRID("Hybrid");

    private final String fuelType;

    EFuelType (String fuelType) {
        this.fuelType = fuelType;
    }

}