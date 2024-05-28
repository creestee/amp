package drivus.repository;

import drivus.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentStatusRepository extends JpaRepository<AppointmentStatus, Long> {
    List<AppointmentStatus> findByAppointmentId(Long appointmentId);
}