package drivus.controller;

import drivus.dto.*;
import drivus.model.ServiceProvider;
import drivus.service.SpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = "/api/sp", produces = MediaType.APPLICATION_JSON_VALUE)
public class SpController {

    private final SpService spService;

    @Operation(summary = "Create Service Provider")
    @ApiResponse(responseCode = "201")
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @PostMapping
    public ResponseEntity<CreateSpResponse> createServiceProvider(@Valid @RequestBody CreateSpRequest createSpRequest) {
        log.info("Create SP request: {}", createSpRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(spService.createServiceProvider(createSpRequest));
    }

    @Operation(summary = "Get Service Provider")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @GetMapping("/{id}")
    public ResponseEntity<SpDTO> getServiceProvider(@PathVariable Long id) {
        log.info("Get SP request: {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(spService.getServiceProvider(id));
    }

    @Operation(summary = "Get all Service Providers")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @GetMapping
    public ResponseEntity<Iterable<SpDTO>> getServiceProviders() {
        log.info("Get all SP request");
        return ResponseEntity.status(HttpStatus.OK)
                .body(spService.getServiceProviders());
    }

    @Operation(summary = "Update Service Provider")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @PutMapping("/{id}")
    public ResponseEntity<ServiceProvider> updateServiceProvider(@PathVariable Long id, @Valid @RequestBody CreateSpRequest createSpRequest) {
        log.info("Update SP request for ID {}: {}", id, createSpRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(spService.updateServiceProvider(id, createSpRequest));
    }

    @Operation(summary = "Delete Service Provider")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceProvider(@PathVariable Long id) {
        log.info("Delete SP request for ID {}", id);
        spService.deleteServiceProvider(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Get Service Provider Rating")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @GetMapping("/{id}/rating")
    public ResponseEntity<RatingResponse> getRating(@PathVariable Long id) {
        log.info("Get SP rating request: {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(spService.getRating(id));
    }

    @Operation(summary = "Rate Service Provider")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @PostMapping("/{id}/rating")
    public ResponseEntity<Void> rateServiceProvider(@PathVariable Long id, @Valid @RequestBody GiveRatingRequest rateSpRequest) {
        log.info("Rate SP request: {}", rateSpRequest);
        spService.rateServiceProvider(id, rateSpRequest.getRating());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}