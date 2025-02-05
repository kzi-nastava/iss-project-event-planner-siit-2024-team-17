package com.ftn.event_hopper.services.reports;

import com.ftn.event_hopper.dtos.reports.CreateReportDTO;
import com.ftn.event_hopper.dtos.reports.CreatedReportDTO;
import com.ftn.event_hopper.dtos.reports.GetReportDTO;
import com.ftn.event_hopper.dtos.users.account.SimpleAccountDTO;
import com.ftn.event_hopper.mapper.reports.ReportDTOMapper;
import com.ftn.event_hopper.models.reports.Report;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.repositories.reports.ReportRepository;
import com.ftn.event_hopper.repositories.users.AccountRepository;
import com.ftn.event_hopper.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReportService {
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ReportDTOMapper reportDTOMapper;

    public List<GetReportDTO> findAll() {
        List<Report> reports = reportRepository.findAll();
        return reportDTOMapper.fromReportListToGetReportDTOList(reports);
    }

    public GetReportDTO findById(UUID id) {
        Report report = reportRepository.findById(id).orElse(null);
        return reportDTOMapper.fromReportToGetReportDTO(report);
    }

    public CreatedReportDTO create(CreateReportDTO reportDTO) {
        Report report = reportDTOMapper.fromCreateReportDTOtoReport(reportDTO);

        Account reporter = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        report.setReporter(reporter);
        report.setTimestamp(LocalDateTime.now());
        this.save(report);

        return reportDTOMapper.fromReportToCreatedReportDTO(report);
    }

    public void delete(UUID id) {
        Report report = reportRepository.findById(id).orElse(null);
        if(report != null) {
            reportRepository.delete(report);
        }
    }

    public void suspend(UUID id) {

        Report report = reportRepository.findById(id).orElse(null);
        if(report != null) {
            Account account = report.getReported();
            account.setSuspensionTimestamp(LocalDateTime.now());
            accountRepository.save(account);

            reportRepository.delete(report);
        }
    }

    public Report save(Report report) {
        return reportRepository.save(report);
    }


}
