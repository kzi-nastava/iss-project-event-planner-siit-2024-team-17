package com.ftn.event_hopper.models.users;

import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.models.solutions.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "service_providers")
public class ServiceProvider extends Person{
    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    @Email(message = "Company email must be in a valid format")
    private String companyEmail;

    @Column(nullable = false)
    @Pattern(regexp = "^\\+?\\d{8,}$", message = "Company phone number must be at least 8 digits long and may optionally start with a '+'")
    private String companyPhoneNumber;

    @Column
    private String companyDescription;

    @Column(nullable = true)
    private LocalTime workStart;

    @Column(nullable = true)
    private LocalTime workEnd;

    @ElementCollection
    @CollectionTable(name = "company_pictures", joinColumns = @JoinColumn(name = "service_provider_id"))
    @Column(name = "picture_url")
    private List<String> companyPhotos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false,  cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_location_id", nullable = false)
    private Location companyLocation;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "service_provider_id")
    private Set<Product> products = new HashSet<>();

}
