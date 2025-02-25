package com.ftn.event_hopper.mapper.reports;

import com.ftn.event_hopper.dtos.reports.CreateReportDTO;
import com.ftn.event_hopper.dtos.reports.CreatedReportDTO;
import com.ftn.event_hopper.dtos.reports.GetReportDTO;
import com.ftn.event_hopper.dtos.users.account.SimpleAccountDTO;
import com.ftn.event_hopper.mapper.users.AccountDTOMapper;
import com.ftn.event_hopper.models.reports.Report;
import com.ftn.event_hopper.models.users.Account;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
        Converter<Account, SimpleAccountDTO> converter = context ->
                accountDTOMapper.fromAccountToSimpleDTO(context.getSource());

        modelMapper.typeMap(Report.class, GetReportDTO.class)
                .addMappings(mapper -> mapper.using(converter)
                        .map(Report::getReported, GetReportDTO::setReported));

        modelMapper.typeMap(Report.class, GetReportDTO.class)
                .addMappings(mapper -> mapper.using(converter)
                        .map(Report::getReporter, GetReportDTO::setReporter));
    }

    public GetReportDTO fromReportToGetReportDTO(Report report) {
        return modelMapper.map(report, GetReportDTO.class);
    }

    public List<GetReportDTO> fromReportListToGetReportDTOList(List<Report> reports) {
        return reports.stream()
                .map(this::fromReportToGetReportDTO)
                .collect(Collectors.toList());
    }


    public Report fromCreateReportDTOtoReport(CreateReportDTO reportDTO) {
        return modelMapper.map(reportDTO, Report.class);
    }

    public CreatedReportDTO fromReportToCreatedReportDTO(Report report) {
        return modelMapper.map(report, CreatedReportDTO.class);
    }

}
