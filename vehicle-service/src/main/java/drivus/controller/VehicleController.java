package drivus.controller;

import drivus.dto.ApiErrorResponse;
import drivus.dto.CreateVehicleRequest;
import drivus.dto.CreateVehicleResponse;
import drivus.model.Vehicle;
import drivus.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = "/api/vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
public class VehicleController {

    private final VehicleService vehicleService;

    @Operation(summary = "Create vehicle")
    @ApiResponse(responseCode = "201")
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @PostMapping
    public ResponseEntity<CreateVehicleResponse> createVehicle(@Valid @RequestBody CreateVehicleRequest createVehicleRequest) {
        log.info("Create vehicle request: {}", createVehicleRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vehicleService.createVehicle(createVehicleRequest));
    }

    @Operation(summary = "Get vehicle")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable Long id) {
        log.info("Get vehicle request: {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(vehicleService.getVehicle(id));
    }

    @Operation(summary = "Update vehicle")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @Valid @RequestBody CreateVehicleRequest createVehicleRequest) {
        log.info("Update vehicle request for ID {}: {}", id, createVehicleRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(vehicleService.updateVehicle(id, createVehicleRequest));
    }

    @Operation(summary = "Get vehicle for User Id")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Vehicle>> getVehiclesForUserId(@PathVariable Long id) {
        log.info("Get vehicles for User ID: {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(vehicleService.getVehiclesforUserId(id));
    }
}
