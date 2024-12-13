package com.ftn.event_hopper.services.solutions;


import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.dtos.solutions.ServiceManagementDTO;
import com.ftn.event_hopper.dtos.solutions.SimpleProductDTO;
import com.ftn.event_hopper.mapper.prices.PriceDTOMapper;
import com.ftn.event_hopper.mapper.solutions.ProductDTOMapper;
import com.ftn.event_hopper.models.prices.Price;
import com.ftn.event_hopper.models.shared.ProductStatus;
import com.ftn.event_hopper.models.solutions.Product;
import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.models.ratings.Rating;
import com.ftn.event_hopper.models.users.ServiceProvider;
import com.ftn.event_hopper.repositories.solutions.ProductRepository;
import com.ftn.event_hopper.repositories.solutions.ServiceRepository;
import com.ftn.event_hopper.repositories.user.PersonRepository;
import com.ftn.event_hopper.repositories.user.ServiceProviderRepository;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;
    @Autowired
    private ProductDTOMapper productDTOMapper;



    public Collection<SimpleProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return productDTOMapper.fromProductListToSimpleDTOList(products);
    }

    public SimpleProductDTO findById(UUID id) {
        Product product = productRepository.findById(id).orElse(null);
        return productDTOMapper.fromProductToSimpleDTO(product);
    }

    public Collection<SimpleProductDTO> findTop5(UUID usersId) {
        Person person = personRepository.findById(usersId).orElse(null);
        List<ServiceProvider> providersFromTheSameCity = serviceProviderRepository.findByCompanyLocationCity(person.getLocation().getCity());

        List<Product> allProductsByLocation = new ArrayList<>();
        for (ServiceProvider serviceProvider : providersFromTheSameCity) {
            allProductsByLocation.addAll(serviceProvider.getProducts());
        }

        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : allProductsByLocation) {
            if (product.isAvailable() == false || product.isVisible() == false || product.getStatus()!= ProductStatus.APPROVED || product.isDeleted()==true) {
                continue;
            }
            filteredProducts.add(product);
        }

        filteredProducts.sort(Comparator.comparingDouble((Product product) ->
                product.getRatings().stream()
                        .mapToDouble(Rating::getValue)
                        .average()
                        .orElse(0.0)
        ).reversed());


        List<Product> top5Products = filteredProducts.subList(0, Math.min(filteredProducts.size(), 5));

        return productDTOMapper.fromProductListToSimpleDTOList(top5Products);
    }

    public Page<SimpleProductDTO> findAll(Pageable page,
                                          Boolean isProduct,
                                          Boolean isService,
                                          UUID categoryId,
                                          ArrayList<UUID> eventTypeIds,
                                          Double minPrice,
                                          Double maxPrice,
                                          Boolean isAvailable,
                                          String searchContent,
                                          String sortField,
                                          String sortDirection) {

        Specification<Product> specification = Specification.where((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("isDeleted"), false),
                        criteriaBuilder.equal(root.get("isVisible"), true),
                        criteriaBuilder.equal(root.get("status"), ProductStatus.APPROVED)
                ));



        if (isProduct || isService) {
            specification = specification.and((root, query, criteriaBuilder) -> {
                List<UUID> productIds = productRepository.findProductIds();
                List<UUID> serviceIds = serviceRepository.findServiceIds();

                for (UUID serviceId : serviceIds) {
                    productIds.remove(serviceId);
                }

                Predicate productPredicate = root.get("id").in(productIds);
                Predicate servicePredicate = root.get("id").in(serviceIds);

                // Combining conditions for isProduct and isService
                if (isProduct && isService) {
                    return criteriaBuilder.or(productPredicate, servicePredicate);
                } else if (isProduct && !isService) {
                    return productPredicate;
                } else if (isService && !isProduct) {
                    return servicePredicate;
                }else{
                    return criteriaBuilder.or(productPredicate, servicePredicate);
                }
            });
        }



        if (categoryId != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("category").get("id"), categoryId));
        }

        if (eventTypeIds != null && !eventTypeIds.isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    root.get("eventTypes").get("id").in(eventTypeIds));
        }

        if (minPrice != null) {
            specification = specification.and((root, query, criteriaBuilder) -> {
                Join<Object, Object> pricesJoin = root.join("prices", JoinType.INNER);
                Subquery<LocalDateTime> subquery = query.subquery(LocalDateTime.class);
                Root<Product> subRoot = subquery.from(Product.class);
                Join<Object, Object> subPricesJoin = subRoot.join("prices", JoinType.INNER);
                subquery.select(criteriaBuilder.greatest(subPricesJoin.get("timestamp").as(LocalDateTime.class)))
                        .where(criteriaBuilder.equal(subRoot.get("id"), root.get("id")));

                return criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(pricesJoin.get("finalPrice"), minPrice),
                        criteriaBuilder.equal(pricesJoin.get("timestamp"), subquery)
                );
            });
        }

        // handle list of prices in Product and make comparing to the newest one
        if (maxPrice != null) {
            specification = specification.and((root, query, criteriaBuilder) -> {
                Join<Object, Object> pricesJoin = root.join("prices", JoinType.INNER);
                Subquery<LocalDateTime> subquery = query.subquery(LocalDateTime.class);
                Root<Product> subRoot = subquery.from(Product.class);
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


        Sort sort = Sort.unsorted();
        if (StringUtils.hasText(sortField) && StringUtils.hasText(sortDirection)) {
            sort = switch (sortField) {
                case "basePrice" ->
                        Sort.by("asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC, "prices.basePrice");
                case "discount" ->
                        Sort.by("asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC, "prices.discount");
                case "finalPrice" ->
                        Sort.by("asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC, "prices.finalPrice");
                default ->
                        Sort.by("asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
            };
        }

        Pageable pageableWithSort = PageRequest.of(page.getPageNumber(), page.getPageSize(), sort);

        Page<Product> filteredProducts = productRepository.findAll(specification, pageableWithSort);

        Page<SimpleProductDTO> all = productDTOMapper.fromProductPageToSimpleProductDTOPage(filteredProducts);


        return all;
        }
}
