package drivus.service;

import drivus.dto.CreateSpRequest;
import drivus.dto.CreateSpResponse;
import drivus.dto.RatingResponse;
import drivus.dto.SpDTO;
import drivus.exception.SpNotFoundException;
import drivus.model.ServiceProvider;
import drivus.model.ServiceProviderRating;
import drivus.repository.ServiceProviderRatingRepository;
import drivus.repository.ServiceProviderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SpService {
    private final ServiceProviderRepository serviceProviderRepository;
    private final ServiceProviderRatingRepository serviceProviderRatingRepository;

    @Transactional
    @Caching(put = @CachePut(cacheNames = "service_provider", key = "#result.id"),
            evict = @CacheEvict(cacheNames = "service_providers", allEntries = true))
    public CreateSpResponse createServiceProvider(CreateSpRequest createVehicleRequest) {
        log.info("Creating service provider with name {}", createVehicleRequest.name());
        ServiceProvider sp = new ServiceProvider();
        sp.setName(createVehicleRequest.name());
        sp.setAddress(createVehicleRequest.address());
        sp.setAbout(createVehicleRequest.about());
        sp.setServices(createVehicleRequest.services());
        ServiceProvider createdSp = serviceProviderRepository.saveAndFlush(sp);

        ServiceProviderRating serviceProviderRating = new ServiceProviderRating();
        serviceProviderRating.setServiceProvider(createdSp);
        serviceProviderRating.setReviewsNumber(0L);
        serviceProviderRating.setReviewsSum(0L);
        serviceProviderRatingRepository.saveAndFlush(serviceProviderRating);

        return new CreateSpResponse(sp.getId(), sp.getName(), sp.getAddress(), sp.getAbout(), sp.getServices());
    }

    @Caching(evict = @CacheEvict(cacheNames = "rating", allEntries = true))
    public void rateServiceProvider(Long id, Integer rating) {
        ServiceProvider sp = serviceProviderRepository.findById(id)
                .orElseThrow(() -> new SpNotFoundException("Service provider not found with id " + id));

        ServiceProviderRating serviceProviderRating = serviceProviderRatingRepository.findByServiceProvider(sp)
                .orElseThrow(() -> new SpNotFoundException("Service provider rating not found with id " + id));

        serviceProviderRating.setReviewsNumber(serviceProviderRating.getReviewsNumber() + 1);
        serviceProviderRating.setReviewsSum(serviceProviderRating.getReviewsSum() + rating);

        serviceProviderRatingRepository.saveAndFlush(serviceProviderRating);
    }

    @Cacheable(value = "rating", key = "#id")
    public RatingResponse getRating(Long id) {
        ServiceProvider sp = serviceProviderRepository.findById(id)
                .orElseThrow(() -> new SpNotFoundException("Service provider not found with id " + id));

        ServiceProviderRating serviceProviderRating = serviceProviderRatingRepository.findByServiceProvider(sp)
                .orElseThrow(() -> new SpNotFoundException("Service provider rating not found with id " + id));

        return new RatingResponse(sp.getId(), getAverageRating(serviceProviderRating));
    }

    @Cacheable(value = "service_provider", key = "#id")
    public SpDTO getServiceProvider(Long id) {
        ServiceProvider sp = serviceProviderRepository.findById(id)
                .orElseThrow(() -> new SpNotFoundException("Service provider not found with id " + id));
        return new SpDTO(sp.getId(), sp.getName(), sp.getAddress(), sp.getAbout(), sp.getServices());
    }

    @Cacheable(value = "service_providers")
    public List<SpDTO> getServiceProviders() {
        log.info("Fetching all service providers");
        List<ServiceProvider> serviceProviders = serviceProviderRepository.findAll();
        return serviceProviders.stream()
                .map(sp -> new SpDTO(sp.getId(), sp.getName(), sp.getAddress(), sp.getAbout(), sp.getServices()))
                .toList();
    }

    @Transactional
    @Caching(put = @CachePut(cacheNames = "service_provider", key = "#result.id"),
            evict = @CacheEvict(cacheNames = "service_providers", allEntries = true))
    public ServiceProvider updateServiceProvider(Long id, CreateSpRequest createSpRequest) {
        ServiceProvider sp = serviceProviderRepository.findById(id)
                .orElseThrow(() -> new SpNotFoundException("Service provider not found with id " + id));
        sp.setName(createSpRequest.name());
        sp.setAddress(createSpRequest.address());
        sp.setAbout(createSpRequest.about());
        sp.setServices(createSpRequest.services());
        return serviceProviderRepository.saveAndFlush(sp);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "service_provider", key = "#result.id"),
            @CacheEvict(cacheNames = "service_providers", allEntries = true)
    })
    public void deleteServiceProvider(Long id) {
        ServiceProvider sp = serviceProviderRepository.findById(id)
                .orElseThrow(() -> new SpNotFoundException("Service provider not found with id " + id));

        serviceProviderRepository.deleteById(id);
    }

    private Double getAverageRating(ServiceProviderRating serviceProviderRating) {
        double averageRating = (double) serviceProviderRating.getReviewsSum() / serviceProviderRating.getReviewsNumber();
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(averageRating));
    }
}
