package com.ftn.event_hopper.services.users;

import com.ftn.event_hopper.dtos.users.serviceProvider.*;
import com.ftn.event_hopper.mapper.comments.CommentDTOMapper;
import com.ftn.event_hopper.mapper.users.ServiceProviderDTOMapper;
import com.ftn.event_hopper.models.comments.Comment;
import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.models.ratings.Rating;
import com.ftn.event_hopper.models.shared.CommentStatus;
import com.ftn.event_hopper.models.users.ServiceProvider;
import com.ftn.event_hopper.repositories.users.ServiceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class ServiceProviderService {
    @Autowired
    private ServiceProviderRepository serviceProviderRepository;
    @Autowired
    private ServiceProviderDTOMapper serviceProviderDTOMapper;
    @Autowired
    private CommentDTOMapper commentDTOMapper;


    public SimpleServiceProviderDTO findOne(UUID id) {
        ServiceProvider serviceProvider = serviceProviderRepository.findById(id).orElseGet(null);
        return serviceProviderDTOMapper.fromServiceProviderToSimpleDTO(serviceProvider);
    }

    public List<SimpleServiceProviderDTO> findAll() {
        List<ServiceProvider> serviceProviders = serviceProviderRepository.findAll();
        return serviceProviderDTOMapper.fromServiceProviderListToSimpleDTOList(serviceProviders);
    }

    public CreatedServiceProviderDTO create(CreateServiceProviderDTO providerDTO){
        ServiceProvider provider = serviceProviderDTOMapper.fromCreateServiceProviderDTOToServiceProvider(providerDTO);
        provider.setWorkStart(LocalTime.now());
        this.save(provider);
        return serviceProviderDTOMapper.fromServiceProviderToCreatedDTO(provider);
    }

    public ServiceProvider save(ServiceProvider provider) {
        return serviceProviderRepository.save(provider);
    }


    public UpdatedServiceProviderDTO update(UUID id, UpdateServiceProviderDTO providerDTO){
        ServiceProvider provider = serviceProviderRepository.findById(id).orElseGet(null);
        if(provider!= null){
            provider.setCompanyDescription(providerDTO.getCompanyDescription());
            provider.setCompanyPhotos(providerDTO.getCompanyPhotos());
            provider.setWorkStart(providerDTO.getWorkStart());
            provider.setWorkEnd(providerDTO.getWorkEnd());
            Location location = provider.getCompanyLocation();
            location.setCity(providerDTO.getCompanyLocation().getCity());
            location.setAddress(providerDTO.getCompanyLocation().getAddress());
            location.setLatitude(providerDTO.getCompanyLocation().getLatitude());
            location.setLongitude(providerDTO.getCompanyLocation().getLongitude());
            provider.setCompanyLocation(location);
            this.save(provider);
        }
        return serviceProviderDTOMapper.fromServiceProviderToUpdatedDTO(provider);
    }


    public boolean remove(UUID id){
        ServiceProvider provider = serviceProviderRepository.findById(id).orElseGet(null);
        if(provider != null){
            provider.setWorkEnd(LocalTime.now());
            this.save(provider);
            return true;
        }
        return false;
    }


    public ServiceProviderDetailsDTO getDetails(UUID id) {
        ServiceProvider provider = serviceProviderRepository.findById(id).orElseGet(null);

        if (provider == null) {
            return null;
        }

        List<Rating> ratings = provider.getProducts().stream()
        .flatMap(product -> product.getRatings().stream())
        .toList();

        List<Comment> comments = provider.getProducts().stream()
                .flatMap(product -> product.getComments().stream())
                .filter(comment -> comment.getStatus() == CommentStatus.ACCEPTED)
                .toList();

        ServiceProviderDetailsDTO details = serviceProviderDTOMapper.fromServiceProviderToDetailsDTO(provider);

        details.setRating(ratings.stream().mapToDouble(Rating::getValue).average().orElse(0.0));
        details.setComments(commentDTOMapper.fromCommentListToSimplecommentDTOCollection(comments));

        return details;
    }
}
