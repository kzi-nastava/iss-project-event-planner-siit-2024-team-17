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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

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

        Page<Product> solutionsPage = productRepository.findAll(page);
        List<Product> products = solutionsPage.getContent();

        products = products.stream()
                .filter(product -> !product.isDeleted()) // Exclude deleted products
                .filter(Product::isVisible)             // Include only visible products
                .filter(product -> product.getStatus() == ProductStatus.APPROVED) // Include only approved products
                .collect(Collectors.toList());

        if (isProduct || isService) {
            products = products.stream()
                    .filter(product -> {
                        String discriminatorValue = product.getClass().getAnnotation(DiscriminatorValue.class).value();
                        return (isProduct && "PRODUCT".equals(discriminatorValue)) ||
                                (isService && "SERVICE".equals(discriminatorValue));
                    })
                    .collect(Collectors.toList());
        }


        if (categoryId != null) {
            products = products.stream()
                    .filter(product -> product.getCategory() != null && product.getCategory().getId().equals(categoryId))
                    .collect(Collectors.toList());
        }

        if (eventTypeIds != null && !eventTypeIds.isEmpty()) {
            products = products.stream()
                    .filter(product -> product.getEventTypes().stream()
                    .anyMatch(eventType -> eventTypeIds.contains(eventType.getId())))
                    .collect(Collectors.toList());
        }

        if (minPrice != null || maxPrice != null) {
            products = products.stream()
                    .filter(product -> {
                        Price price = product.getPrices().get(product.getPrices().size() - 1);
                        boolean matchesMin = (minPrice == null || price.getFinalPrice() >= minPrice);
                        boolean matchesMax = (maxPrice == null || price.getFinalPrice() <= maxPrice);
                        return matchesMin && matchesMax;
                    })
                    .collect(Collectors.toList());
        }

        if (searchContent != null && !searchContent.isEmpty()) {
            String searchLower = searchContent.toLowerCase();
            products = products.stream()
                    .filter(product -> product.getName().toLowerCase().contains(searchLower) ||
                    (product.getDescription() != null && product.getDescription().toLowerCase().contains(searchLower)))
                    .collect(Collectors.toList());
        }

        List<SimpleProductDTO> filteredSolutions = productDTOMapper.fromProductListToSimpleDTOList(products);


        return new PageImpl<>(filteredSolutions, page, filteredSolutions.size());

    }
}
