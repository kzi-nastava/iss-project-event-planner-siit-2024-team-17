package com.ftn.event_hopper.mapper.prices;

import com.ftn.event_hopper.dtos.prices.SimplePriceDTO;
import com.ftn.event_hopper.dtos.prices.UpdatePriceDTO;
import com.ftn.event_hopper.dtos.prices.UpdatedPriceDTO;
import com.ftn.event_hopper.mapper.categories.CategoryDTOMapper;
import com.ftn.event_hopper.models.prices.Price;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PriceDTOMapper {
    private final ModelMapper modelMapper;

    public PriceDTOMapper(ModelMapper modelMapper, CategoryDTOMapper categoryDTOMapper) {
        this.modelMapper = modelMapper;
    }


    public SimplePriceDTO fromPriceToSimplePriceDTO(Price recentPrice) {
        return modelMapper.map(recentPrice, SimplePriceDTO.class);
    }

    public UpdatedPriceDTO fromPriceToUpdatedPriceDTO(Price newPrice) {
        return modelMapper.map(newPrice, UpdatedPriceDTO.class);
    }

    public Price fromUpdatePriceDTOToPrice(UpdatePriceDTO price) {
        return modelMapper.map(price, Price.class);
    }
}
