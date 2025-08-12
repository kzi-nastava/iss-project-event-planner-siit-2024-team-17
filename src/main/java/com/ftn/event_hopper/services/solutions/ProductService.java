package com.ftn.event_hopper.services.solutions;

import com.ftn.event_hopper.dtos.comments.CreateCommentDTO;
import com.ftn.event_hopper.dtos.comments.CreatedCommentDTO;
import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.dtos.messages.ConversationPreviewDTO;
import com.ftn.event_hopper.dtos.notifications.CreateNotificationDTO;
import com.ftn.event_hopper.dtos.prices.PriceManagementDTO;
import com.ftn.event_hopper.dtos.prices.UpdatePriceDTO;
import com.ftn.event_hopper.dtos.prices.UpdatedPriceDTO;
import com.ftn.event_hopper.dtos.ratings.CreateProductRatingDTO;
import com.ftn.event_hopper.dtos.ratings.CreatedProductRatingDTO;
import com.ftn.event_hopper.dtos.solutions.*;
import com.ftn.event_hopper.mapper.events.EventDTOMapper;
import com.ftn.event_hopper.mapper.prices.PriceDTOMapper;
import com.ftn.event_hopper.mapper.solutions.ProductDTOMapper;
import com.ftn.event_hopper.mapper.users.ServiceProviderDTOMapper;
import com.ftn.event_hopper.models.blocks.Block;
import com.ftn.event_hopper.models.categories.Category;
import com.ftn.event_hopper.models.comments.Comment;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.prices.Price;
import com.ftn.event_hopper.models.ratings.Rating;
import com.ftn.event_hopper.models.shared.CategoryStatus;
import com.ftn.event_hopper.models.shared.CommentStatus;
import com.ftn.event_hopper.models.shared.ProductStatus;
import com.ftn.event_hopper.models.solutions.Product;
import com.ftn.event_hopper.models.solutions.Service;
import com.ftn.event_hopper.models.users.*;
import com.ftn.event_hopper.repositories.blocking.BlockingRepository;
import com.ftn.event_hopper.repositories.categoies.CategoryRepository;
import com.ftn.event_hopper.repositories.eventTypes.EventTypeRepository;
import com.ftn.event_hopper.repositories.prices.PriceRepository;
import com.ftn.event_hopper.repositories.reservations.ReservationRepository;
import com.ftn.event_hopper.repositories.solutions.ProductRepository;
import com.ftn.event_hopper.repositories.solutions.ServiceRepository;
import com.ftn.event_hopper.repositories.users.AccountRepository;
import com.ftn.event_hopper.repositories.users.EventOrganizerRepository;
import com.ftn.event_hopper.repositories.users.PersonRepository;
import com.ftn.event_hopper.repositories.users.ServiceProviderRepository;
import com.ftn.event_hopper.services.notifications.NotificationService;
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
import java.util.stream.Collectors;

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
    private BlockingRepository blockingRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private EventTypeRepository eventTypeRepository;
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
    @Autowired
    private NotificationService notificationService;


    public Collection<SimpleProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return productDTOMapper.fromProductListToSimpleDTOList(products);
    }

    public SimpleProductDTO findById(UUID id) {
        Product product = productRepository.findById(id).orElse(null);
        return productDTOMapper.fromProductToSimpleDTO(product);
    }

    public CreatedProductDTO create(CreateProductDTO product) {
        Product newProduct = productDTOMapper.fromCreateProductDTOToProduct(product);

        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (account == null) {
            throw new EntityNotFoundException("Account not found");
        }
        ServiceProvider serviceProvider = serviceProviderRepository.findById(account.getPerson().getId()).orElse(null);

        if (serviceProvider == null) {
            throw new EntityNotFoundException("Service provider not found");
        }

        if (product.getPictures() != null && product.getPictures().isEmpty()) {
            throw new IllegalArgumentException("Pictures are required");
        }

        newProduct.setId(null);
        newProduct.setDeleted(false);
        newProduct.setEditTimestamp(LocalDateTime.now());

        Category category = categoryRepository.findById(product.getCategoryId()).orElse(null);

        if (category == null) {
            throw new EntityNotFoundException("Category not found");
        }

        newProduct.setCategory(category);

        newProduct.setStatus(ProductStatus.APPROVED);
        if (category.getStatus() == CategoryStatus.PENDING) {
            System.out.println("upaooo" + serviceProvider.getName());
            newProduct.setStatus(ProductStatus.PENDING);
            CreateNotificationDTO notificationDTO = new CreateNotificationDTO(
                    "You have new category to review!",
                    null,
                    newProduct.getId()
            );

            //check this
            UUID personId = personRepository.findByType(PersonType.ADMIN).get(0).getId();
            notificationService.sendNotification(notificationDTO, personId );

            //send notification
        }

        newProduct.setEventTypes(new HashSet<>(eventTypeRepository.findAllById(product.getEventTypesIds())));

        double finalPrice = product.getBasePrice() * (1 - product.getDiscount() / 100);
        finalPrice = Math.round(finalPrice * 100.0) / 100.0;

        Price price = new Price(
                null,
                product.getBasePrice(),
                product.getDiscount(),
                finalPrice,
                LocalDateTime.now());
        List<Price> prices = new ArrayList<>();
        prices.add(price);
        newProduct.setPrices(prices);

        newProduct = productRepository.save(newProduct);
        productRepository.flush();

        serviceProvider.getProducts().add(newProduct);
        serviceProviderRepository.save(serviceProvider);
        serviceProviderRepository.flush();

        return productDTOMapper.fromProductToCreatedProductDTO(newProduct);
    }

    public CreatedProductDTO update(UpdateProductDTO product, UUID id) {
        Product existing = productRepository.findById(id).orElse(null);

        if (existing == null || existing.isDeleted()) {
            throw new EntityNotFoundException("Product not found");
        }

        CreateProductDTO createProduct = new CreateProductDTO();
        createProduct.setName(product.getName());
        createProduct.setDescription(product.getDescription());
        createProduct.setPictures(product.getPictures());
        createProduct.setAvailable(product.isAvailable());
        createProduct.setVisible(product.isVisible());
        createProduct.setBasePrice(existing.getCurrentPrice().getBasePrice());
        createProduct.setDiscount(existing.getCurrentPrice().getDiscount());
        createProduct.setEventTypesIds(product.getEventTypesIds());
        createProduct.setCategoryId(existing.getCategory().getId());


        if (product.getPictures() != null && product.getPictures().isEmpty()) {
            throw new IllegalArgumentException("Pictures are required");
        }

        deleteProduct(existing.getId());
        CreatedProductDTO created = create(createProduct);


        return created;
    }


    public boolean deleteProduct(UUID id) {
        Product existing = productRepository.findById(id).orElse(null);
        if (existing == null || existing.isDeleted()) {
            return false;
        }

        existing.setDeleted(true);
        productRepository.save(existing);
        productRepository.flush();
        return true;
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

            //check if they are blocked
            ServiceProvider serviceProvider = serviceProviderRepository.findByProductsContaining(product);
            Account blocked = accountRepository.findByPersonId(serviceProvider.getId()).orElse(null);
            if (blocked != null && blocked.getType() == PersonType.EVENT_ORGANIZER) {
                Block block = blockingRepository.findByWhoAndBlocked(account,blocked).orElse(null);
                if (block != null) {
                    continue;
                }
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

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null
                && (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Account)){

            specification = specification.and((root, query, criteriaBuilder) -> {
                Account who = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                Root<ServiceProvider> serviceProviderRoot = query.from(ServiceProvider.class);
                Join<ServiceProvider, Product> productJoin = serviceProviderRoot.join("products", JoinType.INNER);


                Subquery<Account> accountSubquery = query.subquery(Account.class);
                Root<Account> accountRoot = accountSubquery.from(Account.class);
                accountSubquery.select(accountRoot)
                        .where(criteriaBuilder.equal(accountRoot.get("person"), serviceProviderRoot));

                Subquery<Long> blockSubquery = query.subquery(Long.class);
                Root<Block> blockRoot = blockSubquery.from(Block.class);
                blockSubquery.select(criteriaBuilder.count(blockRoot))
                        .where(
                                criteriaBuilder.equal(blockRoot.get("who"), who),
                                criteriaBuilder.equal(blockRoot.get("blocked"), accountSubquery)
                        );

                return criteriaBuilder.and(
                        criteriaBuilder.equal(root, productJoin), // Vraćamo samo proizvode koji su povezani sa ServiceProviderom
                        criteriaBuilder.equal(blockSubquery, 0) );
            });
        }



        Pageable pageableWithSort = PageRequest.of(page.getPageNumber(), page.getPageSize(), sort);

        Page<Product> filteredProducts = productRepository.findAll(specification, pageableWithSort);

        Page<SimpleProductDTO> all = productDTOMapper.fromProductPageToSimpleProductDTOPage(filteredProducts);


        return all;
        }


    public Page<ProductForManagementDTO> findAllForManagement(Pageable page,
                                                              UUID categoryId,
                                                              List<UUID> eventTypeIds,
                                                              Double minPrice,
                                                              Double maxPrice,
                                                              Boolean isAvailable,
                                                              String searchContent,
                                                              String sortField,
                                                              String sortDirection) {

        Specification<Product> specification = Specification.where((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("isDeleted"), false)
                ));

        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (account == null) {
            throw new EntityNotFoundException("Account not found");
        }
        ServiceProvider serviceProvider = serviceProviderRepository.findById(account.getPerson().getId()).orElse(null);

        if (serviceProvider == null) {
            throw new EntityNotFoundException("Service provider not found");
        }
        Set<Product> pupProducts = serviceProvider.getProducts();

        System.out.println("PUP NAME" + serviceProvider.getName());
        specification = specification.and((root, query, criteriaBuilder) -> {
            List<UUID> productIds = productRepository.findProductIds();
            List<UUID> serviceIds = serviceRepository.findServiceIds();

            for (UUID serviceId : serviceIds) {
                productIds.remove(serviceId);
            }

            Set<UUID> pupsProductsIds = pupProducts.stream()
                    .map(Product::getId)
                    .collect(Collectors.toSet());

            productIds.removeIf(productId -> !pupsProductsIds.contains(productId));


            return root.get("id").in(productIds);
        });




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

        Page<ProductForManagementDTO> all = productDTOMapper.fromProductPageToProductForManagementDTOPage(filteredProducts);


        return all;
    }



    public SolutionDetailsDTO findSolutionDetails(UUID id) {
        Product product = productRepository.findById(id).orElse(null);


        if (product == null || product.isDeleted() || !product.isVisible() || product.getStatus() != ProductStatus.APPROVED) {
            return null;
        }
        ServiceProvider provider = serviceProviderRepository.findByProductsContaining(product);
        Account accountProvider = accountRepository.findByPersonId(provider.getId()).orElse(null);

        boolean pendingComment = false;
        boolean pendingRating = false;
        ConversationPreviewDTO conversation = null;
        ArrayList<SimpleEventDTO> applicableEvents = new ArrayList<>();

        Person person = null;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null
                && (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Account)) {
            Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            person = personRepository.findById(account.getPerson().getId()).orElse(null);

            if (accountProvider != null){
                Block block = blockingRepository.findByWhoAndBlocked(account, accountProvider).orElse(null);
                if (block != null) {
                    throw new RuntimeException("Content is not available");
                }
            }

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
        if (!applicableEvents.isEmpty()) {
            solutionDetailsDTO.setApplicableEvents(applicableEvents);

        }

        return solutionDetailsDTO;
    }

    public Collection<PriceManagementDTO> getPricesForManagement() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (account == null) {
            throw new EntityNotFoundException("Account not found");
        }
        ServiceProvider provider = serviceProviderRepository.findById(account.getPerson().getId()).orElse(null);

        if (provider == null) {
            throw new EntityNotFoundException("Provider not found");
        }

        Set<Product> products = provider.getProducts();
        List<PriceManagementDTO> prices = new ArrayList<>();
        for (Product product : products) {
            if (product.isDeleted()){
                continue;
            }
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

        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (account == null) {
            throw new EntityNotFoundException("Account not found");
        }
        ServiceProvider provider = serviceProviderRepository.findById(account.getPerson().getId()).orElse(null);

        if (provider == null) {
            throw new EntityNotFoundException("Provider not found");
        }

        if (!provider.getProducts().contains(product)) {
            throw new IllegalArgumentException("Provider does not own this product");
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
        if (account == null) {
            throw new EntityNotFoundException("Account not found");
        }
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
        if (account == null) {
            throw new EntityNotFoundException("Account not found");
        }

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


