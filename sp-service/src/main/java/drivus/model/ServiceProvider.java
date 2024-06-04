package drivus.model;

import jakarta.persistence.*;
import lombok.Data;
import drivus.util.StringListConverter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "service_providers", schema = "drivus")
@Data
public class ServiceProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "about", columnDefinition="text", nullable = false)
    private String about;

    @Convert(converter = StringListConverter.class)
    @Column(name = "services", nullable = false)
    private List<String> services = new ArrayList<>();

    @OneToOne(mappedBy = "serviceProvider", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ServiceProviderRating serviceProviderRating;
}
