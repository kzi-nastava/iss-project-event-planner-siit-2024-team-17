package com.ftn.event_hopper.models.categories;

import com.ftn.event_hopper.models.eventTypes.EventType;
import com.ftn.event_hopper.models.shared.CategoryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private CategoryStatus status;

    @Column(nullable = false)
    private boolean isDeleted;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinTable(
            name = "category_event_types",
            joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_type_id", referencedColumnName = "id")
    )
    private Collection<EventType> eventTypes;
}
