package drivus.repository;

import drivus.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findFirstByUserIdAndIsOngoingTrue(Long userId);
    List<Appointment> findAllByUserIdAndIsOngoingFalse(Long userId);
}