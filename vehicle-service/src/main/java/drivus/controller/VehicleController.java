package drivus.controller;

import drivus.dto.ApiErrorResponse;
import drivus.dto.CreateVehicleRequest;
import drivus.dto.CreateVehicleResponse;
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
}
