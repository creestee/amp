package drivus.repository;

import drivus.model.ServiceProvider;
import drivus.model.ServiceProviderRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceProviderRatingRepository extends JpaRepository<ServiceProviderRating, Long> {
    Optional<ServiceProviderRating> findByServiceProvider(ServiceProvider serviceProvider);
}
