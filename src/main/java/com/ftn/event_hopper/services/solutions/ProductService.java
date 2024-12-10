package com.ftn.event_hopper.services.solutions;


import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.dtos.solutions.SimpleProductDTO;
import com.ftn.event_hopper.mapper.solutions.ProductDTOMapper;
import com.ftn.event_hopper.models.prices.Price;
import com.ftn.event_hopper.models.shared.ProductStatus;
import com.ftn.event_hopper.models.solutions.Product;
import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.models.ratings.Rating;
import com.ftn.event_hopper.models.users.ServiceProvider;
import com.ftn.event_hopper.repositories.solutions.ProductRepository;
import com.ftn.event_hopper.repositories.user.PersonRepository;
import com.ftn.event_hopper.repositories.user.ServiceProviderRepository;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
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

    public Page<SimpleProductDTO> findAll(Pageable page, boolean isProduct, boolean isService, UUID categoryId, ArrayList<UUID> eventTypeIds, Double minPrice, Double maxPrice, String searchContent) {

        Specification<Product> specification = Specification.where((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("isDeleted"), false),
                        criteriaBuilder.equal(root.get("isVisible"), true),
                        criteriaBuilder.equal(root.get("status"), ProductStatus.APPROVED)
                ));



        if (isProduct || isService) {
            specification = specification.and((root, query, criteriaBuilder) -> {
                // Pretpostavljamo da se diskriminatorna vrednost nalazi u koloni "DTYPE"
                Predicate productPredicate = criteriaBuilder.equal(root.get("type"), "PRODUCT");
                Predicate servicePredicate = criteriaBuilder.equal(root.get("type"), "SERVICE");

                // Kombinovanje uslova za isProduct i isService
                if (isProduct && isService) {
                    return criteriaBuilder.or(productPredicate, servicePredicate);
                } else if (isProduct) {
                    return productPredicate;
                } else {
                    return servicePredicate;
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
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
        }

        if (maxPrice != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
        }

        if (StringUtils.hasText(searchContent)) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchContent.toLowerCase() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + searchContent.toLowerCase() + "%")
                    ));
        }


            return productDTOMapper.fromProductPageToSimpleProductDTOPage(productRepository.findAll(specification, page));

        }
}
