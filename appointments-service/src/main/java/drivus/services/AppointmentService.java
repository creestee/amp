package drivus.services;

import drivus.dto.AppointmentRequest;
import drivus.dto.AppointmentResponse;
import drivus.model.Appointment;
import drivus.repository.AppointmentRepository;
import drivus.exception.AppointmentNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class AppointmentService {

    private final AppointmentRepository repository;

    public AppointmentResponse createAppointment(AppointmentRequest request) {
        log.info("Creating appointment for user {}", request.userId());
        Appointment savedAppointment = repository.save(mapRequestToEntity(request));
        return mapEntityToResponse(savedAppointment);
    }

    @Cacheable(value = "ongoingAppointments", key = "#userId")
    public AppointmentResponse getOngoingAppointmentForUser(Long userId) {
        log.info("Fetching first ongoing appointment for user {}", userId);
        Appointment appointment = repository.findFirstByUserIdAndIsOngoingTrue(userId)
                .orElseThrow(() -> new AppointmentNotFoundException("No ongoing appointment found for user " + userId));
        return mapEntityToResponse(appointment);
    }

    public List<AppointmentResponse> getAllFinishedAppointmentsForUser(Long userId) {
        log.info("Fetching all finished appointments for user {}", userId);
        List<Appointment> appointments = repository.findAllByUserIdAndIsOngoingFalse(userId);
        return appointments.stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "appointments", key = "#id")
    public AppointmentResponse getAppointment(Long id) {
        log.info("Fetching appointment with id {}", id);
        Appointment appointment = repository.findById(id).orElseThrow(() -> new AppointmentNotFoundException("Appointment with id " + id + " not found"));
        return mapEntityToResponse(appointment);
    }

    public List<AppointmentResponse> getAllAppointments() {
        log.info("Fetching all appointments");
        return repository.findAll().stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @CachePut(value = "appointments", key = "#id")
    public AppointmentResponse updateAppointment(Long id, AppointmentRequest request) {
        log.info("Updating appointment with id {}", id);
        Appointment appointment = repository.findById(id).orElseThrow(() -> new AppointmentNotFoundException("Appointment with id " + id + " not found"));
        Appointment updatedAppointment = repository.saveAndFlush(mapRequestUpdateToEntity(appointment, request));
        return mapEntityToResponse(updatedAppointment);
    }

    @CacheEvict(value = "appointments", key = "#id")
    public void deleteAppointment(Long id) {
        log.info("Deleting appointment with id {}", id);
        Appointment appointment = repository.findById(id).orElseThrow(() -> new AppointmentNotFoundException("Appointment with id " + id + " not found"));
        repository.deleteById(appointment.getId());
    }

    private Appointment mapRequestToEntity(AppointmentRequest request) {
        Appointment appointment = new Appointment();
        appointment.setUserId(request.userId());
        appointment.setVehicleId(request.vehicleId());
        appointment.setServiceProviderId(request.serviceProviderId());
        appointment.setAppointmentDate(request.appointmentDate());
        appointment.setServiceType(request.serviceType());
        appointment.setNotes(request.notes());
        appointment.setIsOngoing(request.isOngoing());
        return appointment;
    }

    private Appointment mapRequestUpdateToEntity(Appointment appointment, AppointmentRequest request) {
        appointment.setUserId(request.userId());
        appointment.setVehicleId(request.vehicleId());
        appointment.setServiceProviderId(request.serviceProviderId());
        appointment.setAppointmentDate(request.appointmentDate());
        appointment.setServiceType(request.serviceType());
        appointment.setNotes(request.notes());
        appointment.setIsOngoing(request.isOngoing());
        return appointment;
    }

    private AppointmentResponse mapEntityToResponse(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getUserId(),
                appointment.getVehicleId(),
                appointment.getServiceProviderId(),
                appointment.getAppointmentDate(),
                appointment.getServiceType(),
                appointment.getNotes(),
                appointment.getIsOngoing()
        );
    }
}