package drivus.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "service_providers_rating", schema = "drivus")
@Data
public class ServiceProviderRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "service_provider_id")
    private ServiceProvider serviceProvider;

    @Column(name = "reviews_number", nullable = false)
    private Long reviewsNumber;

    @Column(name = "reviews_sum", nullable = false)
    private Long reviewsSum;

}
