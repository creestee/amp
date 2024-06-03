package drivus.service;

import drivus.dto.CreateVehicleRequest;
import drivus.dto.CreateVehicleResponse;
import drivus.exception.VehicleNotFoundException;
import drivus.model.EFuelType;
import drivus.model.Vehicle;
import drivus.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

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
        vehicle.setUserId(createVehicleRequest.userId());
        vehicle.setYear(createVehicleRequest.year());
        vehicle.setFuelType(EFuelType.valueOf(createVehicleRequest.fuelType()));
        repository.saveAndFlush(vehicle);
        return new CreateVehicleResponse(vehicle.getId(), vehicle.getUserId(), vehicle.getBrand(), vehicle.getModel(), vehicle.getYear(), vehicle.getFuelType());
    }

    @Cacheable(value = "vehicle", key = "#id")
    public Vehicle getVehicle(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found with id " + id));
    }

    @Cacheable(value = "vehicleUser", key = "#userId")
    public List<Vehicle> getVehiclesforUserId(Long userId) {
        log.info("Fetching all vehicles for user {}", userId);
        return repository.findByUserId(userId);
    }

    @Transactional
    @CachePut(value = "vehicle", key = "#id")
    public Vehicle updateVehicle(Long id, CreateVehicleRequest createVehicleRequest) {
        Vehicle vehicle = repository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found with id " + id));;
        vehicle.setBrand(createVehicleRequest.brand());
        vehicle.setUserId(createVehicleRequest.userId());
        vehicle.setModel(createVehicleRequest.model());
        vehicle.setYear(createVehicleRequest.year());
        vehicle.setFuelType(EFuelType.valueOf(createVehicleRequest.fuelType()));
        return repository.save(vehicle);
    }

    @Transactional
    @CacheEvict(value = "vehicle", key = "#id")
    public void deleteVehicle(Long id) {
        Vehicle vehicle = repository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found with id " + id));;;
        repository.delete(vehicle);
    }
}
