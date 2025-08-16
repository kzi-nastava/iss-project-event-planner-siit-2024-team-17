package com.ftn.event_hopper.repositories.users;

import com.ftn.event_hopper.models.categories.Category;
import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.models.users.PersonType;
import com.ftn.event_hopper.models.users.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
    List<Person> findByType(PersonType type);

    @Query("SELECT DISTINCT sp FROM ServiceProvider sp JOIN sp.products p " +
            "WHERE sp.type = :type AND p.category.id = :categoryId")
    List<ServiceProvider> findByTypeAndProductCategoryId(@Param("type") PersonType type,
                                                       @Param("categoryId") UUID categoryId);

}
