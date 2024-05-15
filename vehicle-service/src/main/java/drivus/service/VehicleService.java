package drivus.service;

import drivus.dto.CreateVehicleRequest;
import drivus.dto.CreateVehicleResponse;
import drivus.model.EFuelType;
import drivus.model.Vehicle;
import drivus.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class VehicleService {
    private final VehicleRepository repository;

    @Transactional
    public CreateVehicleResponse createVehicle(CreateVehicleRequest createVehicleRequest) {
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(createVehicleRequest.brand());
        vehicle.setModel(createVehicleRequest.model());
        vehicle.setYear(createVehicleRequest.year());
        vehicle.setFuelType(EFuelType.valueOf(createVehicleRequest.fuelType()));
        repository.saveAndFlush(vehicle);
        return new CreateVehicleResponse(vehicle.getId(), vehicle.getBrand(), vehicle.getModel(), vehicle.getYear(), vehicle.getFuelType());
    }
}
