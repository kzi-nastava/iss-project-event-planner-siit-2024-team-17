package com.ftn.event_hopper.services.solutions;

import com.ftn.event_hopper.dtos.solutions.*;
import com.ftn.event_hopper.mapper.prices.PriceDTOMapper;
import com.ftn.event_hopper.mapper.solutions.ServiceDTOMapper;
import com.ftn.event_hopper.models.prices.Price;
import com.ftn.event_hopper.models.shared.ProductStatus;
import com.ftn.event_hopper.models.solutions.Service;
import com.ftn.event_hopper.repositories.categoies.CategoryRepository;
import com.ftn.event_hopper.repositories.eventTypes.EventTypeRepository;
import com.ftn.event_hopper.repositories.solutions.ServiceRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@org.springframework.stereotype.Service
public class ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceDTOMapper serviceDTOMapper;

    @Autowired
    private PriceDTOMapper priceDTOMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;


    public boolean deleteService(UUID id) {
        Service existing = serviceRepository.findById(id).orElse(null);
        if (existing == null || existing.isDeleted()) {
            return false;
        } 

        existing.setDeleted(true);
        serviceRepository.save(existing);
        serviceRepository.flush();
        return true;
    }

    public Service saveService(Service service) {
        return serviceRepository.save(service);
    }

    public Page<ServiceManagementDTO> searchServicesForManagement(Pageable page,
                                                                  UUID categoryId,
                                                                  List<UUID> eventTypeIds,
                                                                  Double minPrice,
                                                                  Double maxPrice,
                                                                  Boolean isAvailable,
                                                                  String searchContent) {

        Specification<Service> specification = Specification.where((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isDeleted"), false));

        if (categoryId != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("category").get("id"), categoryId));
        }

        if (eventTypeIds != null && !eventTypeIds.isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    root.join("eventTypes").get("id").in(eventTypeIds));
        }

        if (minPrice != null) {
            specification = specification.and((root, query, criteriaBuilder) -> {
                Join<Object, Object> pricesJoin = root.join("prices", JoinType.INNER);
                Subquery<LocalDateTime> subquery = query.subquery(LocalDateTime.class);
                Root<Service> subRoot = subquery.from(Service.class);
                Join<Object, Object> subPricesJoin = subRoot.join("prices", JoinType.INNER);
                subquery.select(criteriaBuilder.greatest(subPricesJoin.get("timestamp").as(LocalDateTime.class)))
                        .where(criteriaBuilder.equal(subRoot.get("id"), root.get("id")));

                return criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(pricesJoin.get("finalPrice"), minPrice),
                        criteriaBuilder.equal(pricesJoin.get("timestamp"), subquery)
                );
            });
        }

        if (maxPrice != null) {
            specification = specification.and((root, query, criteriaBuilder) -> {
                Join<Object, Object> pricesJoin = root.join("prices", JoinType.INNER);
                Subquery<LocalDateTime> subquery = query.subquery(LocalDateTime.class);
                Root<Service> subRoot = subquery.from(Service.class);
                Join<Object, Object> subPricesJoin = subRoot.join("prices", JoinType.INNER);
                subquery.select(criteriaBuilder.greatest(subPricesJoin.get("timestamp").as(LocalDateTime.class)))
                        .where(criteriaBuilder.equal(subRoot.get("id"), root.get("id")));

                return criteriaBuilder.and(
                        criteriaBuilder.lessThanOrEqualTo(pricesJoin.get("finalPrice"), maxPrice),
                        criteriaBuilder.equal(pricesJoin.get("timestamp"), subquery)
                );
            });
        }

        if (isAvailable != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("isAvailable"), isAvailable));
        }

        if (StringUtils.hasText(searchContent)) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchContent.toLowerCase() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + searchContent.toLowerCase() + "%")
                    ));
        }

        Page<Service> filteredServices = serviceRepository.findAll(specification, page);

        Page<ServiceManagementDTO> all = serviceDTOMapper.fromServicePageToServiceManagementDTOPage(filteredServices);

        for (ServiceManagementDTO dto : all) {
            Service service = filteredServices.stream()
                    .filter(s -> s.getId().equals(dto.getId()))
                    .findFirst()
                    .orElse(null);
            if (service != null && service.getPrices() != null && !service.getPrices().isEmpty()) {
                service.getPrices().stream()
                        .max(Comparator.comparing(Price::getTimestamp)).ifPresent(
                                recentPrice -> dto.setPrice(priceDTOMapper.fromPriceToSimplePriceDTO(recentPrice))
                        );

            }
        }

        return all;

    }

    public CreatedServiceDTO create(CreateServiceDTO service) {
        Service newService = serviceDTOMapper.fromCreateServiceDTOToService(service);

        newService.setId(null);
        newService.setDeleted(false);
        newService.setEditTimestamp(LocalDateTime.now());
        newService.setStatus(ProductStatus.APPROVED);

        newService.setCategory(categoryRepository.findById(service.getCategoryId()).orElse(null));

        newService.setEventTypes(new HashSet<>(eventTypeRepository.findAllById(service.getEventTypesIds())));

        Price price = new Price(null, service.getBasePrice(), service.getDiscount(), service.getFinalPrice(), LocalDateTime.now());
        List<Price> prices = new ArrayList<>();
        prices.add(price);
        newService.setPrices(prices);

        newService = serviceRepository.save(newService);
        serviceRepository.flush();

        //TODO: Assign new service to ServiceProvider
        //TODO: Pictures
        return serviceDTOMapper.fromServiceToCreatedServiceDTO(newService);
    }

    public UpdatedServiceDTO update(UpdateServiceDTO service, UUID id) {
        Service existing = serviceRepository.findById(id).orElse(null);
        if (existing == null || existing.isDeleted()) {
            return null;
        }

        existing.setName(service.getName());
        existing.setDescription(service.getDescription());
        existing.setPictures((List<String>) service.getPictures());
        existing.setAvailable(service.isAvailable());
        existing.setVisible(service.isVisible());
        existing.setEventTypes(new HashSet<>(eventTypeRepository.findAllById(service.getEventTypesIds())));
        existing.setDurationMinutes(service.getDurationMinutes());
        existing.setReservationWindowDays(service.getReservationWindowDays());
        existing.setCancellationWindowDays(service.getCancellationWindowDays());
        existing.setAutoAccept(service.isAutoAccept());

        Service updated = serviceRepository.save(existing);
        serviceRepository.flush();
        return serviceDTOMapper.fromServiceToUpdatedServiceDTO(updated);
    }
}
