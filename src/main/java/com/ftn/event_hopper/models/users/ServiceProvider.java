package com.ftn.event_hopper.models.users;

import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.models.solutions.Product;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class ServiceProvider extends Person{
    private String companyName;
    private String companyEmail;
    private String companyDescription;
    private Collection<String> companyPhotos;
    private LocalTime workStart;
    private LocalTime workEnd;

    private Location companyLocation;
    private Collection<Product> products;

}
