package com.ftn.event_hopper.models.users;

import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.models.solutions.Product;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.sql.Time;
import java.time.LocalTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class ServiceProvider extends Person{
    private String companyName;
    private String companyEmail;
    private String companyDescription;
    private List<String> companyPhotos = new ArrayList<>();
    private LocalTime workStart;
    private LocalTime workEnd;

    private Location companyLocation;
    private Set<Product> products = new HashSet<>();

}
