package com.ftn.event_hopper.services.solutions;

import com.ftn.event_hopper.dtos.comments.CreateCommentDTO;
import com.ftn.event_hopper.dtos.comments.CreatedCommentDTO;
import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.dtos.messages.ConversationPreviewDTO;
import com.ftn.event_hopper.dtos.prices.PriceManagementDTO;
import com.ftn.event_hopper.dtos.prices.UpdatePriceDTO;
import com.ftn.event_hopper.dtos.prices.UpdatedPriceDTO;
import com.ftn.event_hopper.dtos.ratings.CreateProductRatingDTO;
import com.ftn.event_hopper.dtos.ratings.CreatedProductRatingDTO;
import com.ftn.event_hopper.dtos.solutions.SimpleProductDTO;
import com.ftn.event_hopper.dtos.solutions.SolutionDetailsDTO;
import com.ftn.event_hopper.mapper.events.EventDTOMapper;
import com.ftn.event_hopper.mapper.prices.PriceDTOMapper;
import com.ftn.event_hopper.mapper.solutions.ProductDTOMapper;
import com.ftn.event_hopper.mapper.users.ServiceProviderDTOMapper;
import com.ftn.event_hopper.models.comments.Comment;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.prices.Price;
import com.ftn.event_hopper.models.ratings.Rating;
import com.ftn.event_hopper.models.shared.CommentStatus;
import com.ftn.event_hopper.models.shared.ProductStatus;
import com.ftn.event_hopper.models.solutions.Product;
import com.ftn.event_hopper.models.solutions.Service;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.users.EventOrganizer;
import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.models.users.ServiceProvider;
import com.ftn.event_hopper.repositories.prices.PriceRepository;
import com.ftn.event_hopper.repositories.reservations.ReservationRepository;
import com.ftn.event_hopper.repositories.solutions.ProductRepository;
import com.ftn.event_hopper.repositories.solutions.ServiceRepository;
import com.ftn.event_hopper.repositories.users.AccountRepository;
import com.ftn.event_hopper.repositories.users.EventOrganizerRepository;
import com.ftn.event_hopper.repositories.users.PersonRepository;
import com.ftn.event_hopper.repositories.users.ServiceProviderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@org.springframework.stereotype.Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private EventOrganizerRepository eventOrganizerRepository;

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;
    @Autowired
    private ProductDTOMapper productDTOMapper;
    @Autowired
    private ServiceProviderDTOMapper serviceProviderDTOMapper;
    @Autowired
    private PriceDTOMapper priceDTOMapper;
    @Autowired
    private EventDTOMapper eventDTOMapper;


    public Collection<SimpleProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return productDTOMapper.fromProductListToSimpleDTOList(products);
    }

    public SimpleProductDTO findById(UUID id) {
        Product product = productRepository.findById(id).orElse(null);
        return productDTOMapper.fromProductToSimpleDTO(product);
    }

    public Collection<SimpleProductDTO> findTop5(UUID usersId) {
        Account account = accountRepository.findById(usersId).orElse(null);
        Person person = account.getPerson();
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
                case "prices" ->
                        Sort.by("asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC, "prices[-1].finalPrice");
                case "name" ->
                        Sort.by("asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
                default -> throw new IllegalStateException("Unexpected value: " + sortField);
            };
        }


        Pageable pageableWithSort = PageRequest.of(page.getPageNumber(), page.getPageSize(), sort);

        Page<Product> filteredProducts = productRepository.findAll(specification, pageableWithSort);

        Page<SimpleProductDTO> all = productDTOMapper.fromProductPageToSimpleProductDTOPage(filteredProducts);


        return all;
        }

    public SolutionDetailsDTO findSolutionDetails(UUID id) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null || product.isDeleted() || !product.isVisible() || product.getStatus() != ProductStatus.APPROVED) {
            return null;
        }
        ServiceProvider provider = serviceProviderRepository.findByProductsContaining(product);

        boolean pendingComment = false;
        boolean pendingRating = false;
        ConversationPreviewDTO conversation = null;
        ArrayList<SimpleEventDTO> applicableEvents = new ArrayList<>();

        Person person = null;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null
                && (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Account)) {
            Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            person = personRepository.findById(account.getPerson().getId()).orElse(null);
            EventOrganizer eventOrganizer = eventOrganizerRepository.findById(person.getId()).orElse(null);

            if (eventOrganizer != null) {
                pendingComment = product.getComments().stream()
                        .noneMatch(comment -> comment.getAuthor().getId().equals(eventOrganizer.getId()));
                pendingRating = product.getRatings().stream()
                        .noneMatch(rating -> rating.getEventOrganizer().getId().equals(eventOrganizer.getId()));

                Account providerAccount = accountRepository.findByPersonId(provider.getId()).orElse(null);
                if (providerAccount != null && !providerAccount.getId().equals(account.getId())) {
                    conversation = new ConversationPreviewDTO();
                    conversation.setUsername(providerAccount.getUsername());
                    conversation.setName(provider.getName());
                    conversation.setSurname(provider.getSurname());
                    conversation.setProfilePictureUrl(provider.getProfilePicture());
                }

                if (product.isAvailable()) {
                    for (Event event : eventOrganizer.getEvents()) {
                        if (product.getEventTypes().stream().anyMatch(et -> event.getEventType().getId().equals(et.getId()))) {
                            applicableEvents.add(eventDTOMapper.fromEventToSimpleDTO(event));
                        }
                    }
                }
            }
        }

        product.setComments(product.getComments().stream()
                .filter(comment -> comment.getStatus() == CommentStatus.ACCEPTED)
                .toList());

        SolutionDetailsDTO solutionDetailsDTO = productDTOMapper.fromProductToSolutionDetailsDTO(product);
        solutionDetailsDTO.setRating(product.getRatings().stream()
                .mapToDouble(Rating::getValue)
                .average()
                .orElse(0.0));


        solutionDetailsDTO.setProvider(serviceProviderDTOMapper.fromServiceProviderToSimpleDTO(provider));

        solutionDetailsDTO.setService(product instanceof Service);

        if (product instanceof Service service) {
            service = (Service) serviceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Service not found"));
            solutionDetailsDTO.setDurationMinutes(service.getDurationMinutes());
            solutionDetailsDTO.setReservationWindowDays(service.getReservationWindowDays());
            solutionDetailsDTO.setCancellationWindowDays(service.getCancellationWindowDays());
        }

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null
                || !(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Account)) {
            return solutionDetailsDTO;
        }


        solutionDetailsDTO.setFavorite(person.getFavoriteProducts().contains(product));
        solutionDetailsDTO.setPendingComment(pendingComment);
        solutionDetailsDTO.setPendingRating(pendingRating);
        solutionDetailsDTO.setConversationInitialization(conversation);
        solutionDetailsDTO.setApplicableEvents(applicableEvents);

        return solutionDetailsDTO;
    }

    public Collection<PriceManagementDTO> getPricesForManagement() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ServiceProvider provider = serviceProviderRepository.findById(account.getPerson().getId()).orElse(null);

        if (provider == null) {
            throw new EntityNotFoundException("Provider not found");
        }

        Set<Product> products = provider.getProducts();
        List<PriceManagementDTO> prices = new ArrayList<>();
        for (Product product : products) {
            Price newestPrice = product.getPrices().get(product.getPrices().size() - 1);

            PriceManagementDTO price = new PriceManagementDTO();
            price.setId(newestPrice.getId());
            price.setProductId(product.getId());
            price.setProductName(product.getName());

            price.setBasePrice(newestPrice.getBasePrice());
            price.setDiscount(newestPrice.getDiscount());
            price.setFinalPrice(newestPrice.getFinalPrice());
            prices.add(price);
        }
        return prices;
    }

    public UpdatedPriceDTO updatePrice(UUID productId, UpdatePriceDTO price) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new EntityNotFoundException("Product not found");
        }

        Price newPrice = new Price();

        newPrice.setBasePrice(price.getBasePrice());
        newPrice.setDiscount(price.getDiscount());

        double finalPrice = newPrice.getBasePrice() * (1 - newPrice.getDiscount()/100);
        newPrice.setFinalPrice(Math.round(finalPrice * 100.0) / 100.0);

        newPrice.setTimestamp(LocalDateTime.now());
        Price savedPrice = priceRepository.save(newPrice);

        product.getPrices().add(savedPrice);

        productRepository.save(product);
        productRepository.flush();

        return priceDTOMapper.fromPriceToUpdatedPriceDTO(newPrice);
    }


    public CreatedProductRatingDTO rateProduct(CreateProductRatingDTO rating) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person person = personRepository.findById(account.getPerson().getId()).orElse(null);

        if (person == null) {
            throw new EntityNotFoundException("Person not found");
        }

        Product product = productRepository.findById(rating.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException("Product not found");
        }

        if (product.getRatings().stream().anyMatch(r -> r.getEventOrganizer().getId().equals(person.getId()))) {
            throw new IllegalArgumentException("Person has already rated this product");
        }

        if (rating.getValue() < 1 || rating.getValue() > 5) {
            throw new IllegalArgumentException("Rating value must be between 1 and 5");
        }

        if (product.getStatus() != ProductStatus.APPROVED) {
            throw new IllegalArgumentException("Product is not approved");
        }

        if (product.isDeleted()) {
            throw new IllegalArgumentException("Product is deleted");
        }

        if (!product.isVisible()) {
            throw new IllegalArgumentException("Product is not visible");
        }

        EventOrganizer eventOrganizer = eventOrganizerRepository.findById(person.getId()).orElse(null);
        if (eventOrganizer == null) {
            throw new EntityNotFoundException("Event organizer not found");
        }

        boolean found = false;
        for (Event event : eventOrganizer.getEvents()) {
            if (reservationRepository.existsByProductAndEvent(product, event)) {
                found = true;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("Event organizer has not reserved this product");
        }

        Rating newRating = new Rating();
        newRating.setId(null);
        newRating.setValue(rating.getValue());
        newRating.setEventOrganizer(eventOrganizer);

        product.getRatings().add(newRating);
        Product p = productRepository.save(product);
        productRepository.flush();

        CreatedProductRatingDTO ret = new CreatedProductRatingDTO();
        ret.setId(p.getRatings().stream().filter(r -> r.getEventOrganizer().getId().equals(person.getId())).findFirst().get().getId());
        ret.setValue(newRating.getValue());
        ret.setProductId(product.getId());

        return ret;
    }

    public CreatedCommentDTO addComment(CreateCommentDTO comment) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person person = personRepository.findById(account.getPerson().getId()).orElse(null);

        if (person == null) {
            throw new EntityNotFoundException("Person not found");
        }

        Product product = productRepository.findById(comment.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException("Product not found");
        }

        if (product.getComments().stream().anyMatch(r -> r.getAuthor().getId().equals(person.getId()))) {
            throw new IllegalArgumentException("Person has already commented on this product");
        }

        if (product.getStatus() != ProductStatus.APPROVED) {
            throw new IllegalArgumentException("Product is not approved");
        }

        if (product.isDeleted()) {
            throw new IllegalArgumentException("Product is deleted");
        }

        if (!product.isVisible()) {
            throw new IllegalArgumentException("Product is not visible");
        }

        EventOrganizer eventOrganizer = eventOrganizerRepository.findById(person.getId()).orElse(null);
        if (eventOrganizer == null) {
            throw new EntityNotFoundException("Event organizer not found");
        }

        boolean found = false;
        for (Event event : eventOrganizer.getEvents()) {
            if (reservationRepository.existsByProductAndEvent(product, event)) {
                found = true;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("Event organizer has not reserved this product");
        }

        Comment newComment = new Comment();
        newComment.setId(null);
        newComment.setContent(comment.getContent());
        newComment.setAuthor(eventOrganizer);
        newComment.setStatus(CommentStatus.PENDING);


        product.getComments().add(newComment);
        Product p = productRepository.save(product);
        productRepository.flush();

        CreatedCommentDTO ret = new CreatedCommentDTO();
        ret.setId(p.getComments().stream().filter(c -> c.getAuthor().getId().equals(person.getId())).findFirst().get().getId());
        ret.setContent(newComment.getContent());
        ret.setStatus(newComment.getStatus());

        return ret;
    }
}


