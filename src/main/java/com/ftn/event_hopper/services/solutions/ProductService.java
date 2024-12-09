package com.ftn.event_hopper.services.solutions;


import com.ftn.event_hopper.dtos.solutions.SimpleProductDTO;
import com.ftn.event_hopper.mapper.solutions.ProductDTOMapper;
import com.ftn.event_hopper.models.shared.ProductStatus;
import com.ftn.event_hopper.models.solutions.Product;
import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.models.ratings.Rating;
import com.ftn.event_hopper.models.users.ServiceProvider;
import com.ftn.event_hopper.repositories.solutions.ProductRepository;
import com.ftn.event_hopper.repositories.user.PersonRepository;
import com.ftn.event_hopper.repositories.user.ServiceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.*;

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

}
