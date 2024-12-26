package com.ftn.event_hopper.repositories.categoies;

import com.ftn.event_hopper.models.categories.Category;
import com.ftn.event_hopper.models.shared.CategoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    List<Category> findByStatus(CategoryStatus categoryStatus);

    List<Category> findByStatusAndIsDeletedFalse(CategoryStatus categoryStatus);

    @Query("SELECT c FROM Category c JOIN c.eventTypes et WHERE et.id = :eventTypeId")
    List<Category> findByEventTypeId(@Param("eventTypeId") UUID eventTypeId);
}
