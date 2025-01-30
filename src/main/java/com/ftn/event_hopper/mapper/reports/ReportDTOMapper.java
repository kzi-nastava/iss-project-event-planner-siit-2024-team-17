package com.ftn.event_hopper.mapper.reports;

import com.ftn.event_hopper.mapper.users.AccountDTOMapper;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ReportDTOMapper {
    private final ModelMapper modelMapper;
    private final AccountDTOMapper accountDTOMapper;

    public ReportDTOMapper(ModelMapper modelMapper, AccountDTOMapper accountDTOMapper) {
        this.modelMapper = modelMapper;
        this.accountDTOMapper = accountDTOMapper;
        configureMappings();
    }

    public void configureMappings() {
        Converter<>
    }
}
