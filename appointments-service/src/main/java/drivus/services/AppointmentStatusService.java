package drivus.services;

import drivus.dto.AppointmentStatusRequest;
import drivus.dto.AppointmentStatusResponse;
import drivus.model.AppointmentStatus;
import drivus.repository.AppointmentStatusRepository;
import drivus.exception.AppointmentStatusNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class AppointmentStatusService {

    private final AppointmentStatusRepository repository;
    private final RabbitTemplate rabbitTemplate;

    public AppointmentStatusResponse createAppointmentStatus(AppointmentStatusRequest request) {
        log.info("Creating appointment status for appointment {}", request.appointmentId());
        AppointmentStatus savedAppointmentStatus = repository.save(mapRequestToEntity(request));

        AppointmentStatusResponse response = mapEntityToResponse(savedAppointmentStatus);

        rabbitTemplate.convertAndSend("appointmentStatusExchange", "appointmentStatusCreated", response);

        return response;
    }

    public AppointmentStatusResponse getAppointmentStatus(Long id) {
        log.info("Fetching appointment status with id {}", id);
        AppointmentStatus appointmentStatus = repository.findById(id).orElseThrow(() -> new AppointmentStatusNotFoundException("Appointment status with id " + id + " not found"));
        return mapEntityToResponse(appointmentStatus);
    }

    @Cacheable(value = "appointmentStatuses", key = "#appointmentId")
    public List<AppointmentStatusResponse> getAllAppointmentStatuses(Long appointmentId) {
        log.info("Fetching all appointment statuses for appointment {}", appointmentId);
        return repository.findByAppointmentId(appointmentId).stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @CachePut(value = "appointmentStatuses", key = "#id")
    public AppointmentStatusResponse updateAppointmentStatus(Long id, AppointmentStatusRequest request) {
        log.info("Updating appointment status with id {}", id);
        AppointmentStatus appointmentStatus = repository.findById(id).orElseThrow(() -> new AppointmentStatusNotFoundException("Appointment status with id " + id + " not found"));
        AppointmentStatus updatedAppointmentStatus = repository.save(mapRequestUpdateToEntity(appointmentStatus, request));
        return mapEntityToResponse(updatedAppointmentStatus);
    }

    @CacheEvict(value = "appointmentStatuses", key = "#id")
    public void deleteAppointmentStatus(Long id) {
        log.info("Deleting appointment status with id {}", id);
        AppointmentStatus appointmentStatus = repository.findById(id).orElseThrow(() -> new AppointmentStatusNotFoundException("Appointment status with id " + id + " not found"));
        repository.deleteById(appointmentStatus.getId());
    }

    private AppointmentStatus mapRequestToEntity(AppointmentStatusRequest request) {
        AppointmentStatus appointmentStatus = new AppointmentStatus();
        appointmentStatus.setAppointmentId(request.appointmentId());
        appointmentStatus.setStatus(request.status());
        appointmentStatus.setCreatedAt(LocalDateTime.now());
        return appointmentStatus;
    }

    private AppointmentStatus mapRequestUpdateToEntity(AppointmentStatus appointmentStatus, AppointmentStatusRequest request) {
        appointmentStatus.setAppointmentId(request.appointmentId());
        appointmentStatus.setStatus(request.status());
        appointmentStatus.setCreatedAt(LocalDateTime.now());
        return appointmentStatus;
    }

    private AppointmentStatusResponse mapEntityToResponse(AppointmentStatus appointmentStatus) {
        return new AppointmentStatusResponse(
                appointmentStatus.getId(),
                appointmentStatus.getAppointmentId(),
                appointmentStatus.getStatus(),
                appointmentStatus.getCreatedAt()
        );
    }
}