package drivus.controller;

import drivus.dto.AppointmentStatusRequest;
import drivus.dto.AppointmentStatusResponse;
import drivus.services.AppointmentStatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/appointments/status")
public class AppointmentStatusController {

    private final AppointmentStatusService appointmentStatusService;

    @PostMapping
    public ResponseEntity<AppointmentStatusResponse> createAppointmentStatus(@RequestBody AppointmentStatusRequest request) {
        return new ResponseEntity<>(appointmentStatusService.createAppointmentStatus(request), HttpStatus.CREATED);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<AppointmentStatusResponse> getAppointmentStatus(@PathVariable Long id) {
//        return new ResponseEntity<>(appointmentStatusService.getAppointmentStatus(id), HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<List<AppointmentStatusResponse>> getAllAppointmentsStatus(@PathVariable Long id) {
        return new ResponseEntity<>(appointmentStatusService.getAllAppointmentStatuses(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentStatusResponse> updateAppointmentStatus(@PathVariable Long id, @RequestBody AppointmentStatusRequest request) {
        return new ResponseEntity<>(appointmentStatusService.updateAppointmentStatus(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointmentStatus(@PathVariable Long id) {
        appointmentStatusService.deleteAppointmentStatus(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
