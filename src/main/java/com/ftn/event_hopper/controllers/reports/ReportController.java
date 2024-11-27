package com.ftn.event_hopper.controllers.reports;

import com.ftn.event_hopper.dtos.reports.CreateReportDTO;
import com.ftn.event_hopper.dtos.reports.CreatedReportDTO;
import com.ftn.event_hopper.dtos.reports.GetReportDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetReportDTO>> getReports(){

        Collection<GetReportDTO> reports = new ArrayList<GetReportDTO>();

        GetReportDTO report1 = new GetReportDTO();
        report1.setId(UUID.randomUUID());
        report1.setReason("jfniwjfiueigurugnergr");
        report1.setTimestamp(LocalDateTime.now());
        report1.setReportedId(UUID.randomUUID());
        report1.setReporterId(UUID.randomUUID());

        GetReportDTO report2 = new GetReportDTO();
        report2.setId(UUID.randomUUID());
        report2.setReason("jfniwjfiueigurugnergr efoiwemdme");
        report2.setTimestamp(LocalDateTime.now());
        report2.setReportedId(UUID.randomUUID());
        report2.setReporterId(UUID.randomUUID());


        reports.add(report1);
        reports.add(report2);

        return new ResponseEntity<Collection<GetReportDTO>>(reports, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetReportDTO> getReport(@RequestParam UUID reportId){

        GetReportDTO report = new GetReportDTO();

        if (report == null) {
            return new ResponseEntity<GetReportDTO>(HttpStatus.NOT_FOUND);
        }

        report.setId(reportId);
        report.setReason("jfniwjfiueigurugnergr");
        report.setTimestamp(LocalDateTime.now());
        report.setReportedId(UUID.randomUUID());
        report.setReporterId(UUID.randomUUID());

        return new ResponseEntity<GetReportDTO>(report,HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedReportDTO> createReport(@RequestBody CreateReportDTO report){
        CreatedReportDTO createdReport = new CreatedReportDTO();
        createdReport.setId(UUID.randomUUID());
        createdReport.setReason(report.getReason());
        createdReport.setTimestamp(LocalDateTime.now());
        createdReport.setReported(report.getReported());
        createdReport.setReporter(report.getReporter());

        return new ResponseEntity<CreatedReportDTO>(createdReport, HttpStatus.CREATED);
    }


}
