package com.ftn.event_hopper.models.users;

import com.ftn.event_hopper.models.events.Event;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "event_organizers")
public class EventOrganizer extends Person{

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "event_id")
    private Set<Event> events = new HashSet<>();
}
