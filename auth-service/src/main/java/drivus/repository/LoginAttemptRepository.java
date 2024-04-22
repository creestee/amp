package drivus.repository;

import drivus.model.LoginAttempt;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {
    @Query(value = "SELECT la FROM LoginAttempt la WHERE la.email = :email ORDER BY la.createdAt DESC")
    List<LoginAttempt> findRecentByEmail(@Param("email") String email, Pageable pageable);

}