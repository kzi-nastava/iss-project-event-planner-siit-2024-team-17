package com.ftn.event_hopper.models.solutions;

import com.ftn.event_hopper.models.categories.Category;
import com.ftn.event_hopper.models.comments.Comment;
import com.ftn.event_hopper.models.eventTypes.EventType;
import com.ftn.event_hopper.models.prices.Price;
import com.ftn.event_hopper.models.ratings.Rating;
import com.ftn.event_hopper.models.shared.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 1000)
    private String description;

    @ElementCollection
    @CollectionTable(name = "product_pictures", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "picture_url")
    private List<String> pictures = new ArrayList<>();

    @Column(nullable = false)
    private boolean isAvailable;

    @Column(nullable = false)
    private boolean isVisible;

    @Column(nullable = false)
    private ProductStatus status;

    @Column(nullable = false)
    private LocalDateTime editTimestamp;

    @Column(nullable = false)
    private boolean isDeleted;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "product_prices",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "price_id", referencedColumnName = "id")

    )
    private List<Price> prices = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private Set<Rating> ratings = new HashSet<Rating>();

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_event_types",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_type_id", referencedColumnName = "id")
    )
    private Set<EventType> eventTypes = new HashSet<EventType>();

    public Price getCurrentPrice() {
        return prices.stream()
                .max(Comparator.comparing(Price::getTimestamp))
                .orElse(null);
    }

    public Price getPriceAtTimestamp(LocalDateTime timestamp) {
        return prices.stream()
                .filter(price -> price.getTimestamp().isBefore(timestamp))
                .max(Comparator.comparing(Price::getTimestamp))
                .orElse(null);
    }
}
