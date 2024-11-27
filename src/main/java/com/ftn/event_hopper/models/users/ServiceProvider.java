package com.ftn.event_hopper.models.users;

import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.models.solutions.Product;
import lombok.*;

import java.sql.Time;
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
    private String[] companyPhotos;
    private Time workStart;
    private Time workEnd;

    private Location companyLocation;
    private Collection<Product> products;

}
