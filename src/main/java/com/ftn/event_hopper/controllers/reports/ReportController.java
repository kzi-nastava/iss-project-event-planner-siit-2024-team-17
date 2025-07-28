package com.ftn.event_hopper.controllers.reports;

import com.ftn.event_hopper.dtos.reports.CreateReportDTO;
import com.ftn.event_hopper.dtos.reports.CreatedReportDTO;
import com.ftn.event_hopper.dtos.reports.GetReportDTO;
import com.ftn.event_hopper.services.reports.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetReportDTO>> getReports(){

        Collection<GetReportDTO> reports = reportService.findAll();
        if(reports == null) {
            return new ResponseEntity<Collection<GetReportDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetReportDTO> getReport(@PathVariable UUID id){

        GetReportDTO report = reportService.findById(id);

        if (report == null) {
            return new ResponseEntity<GetReportDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(report,HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedReportDTO> createReport(@RequestBody CreateReportDTO report){
        return new ResponseEntity<CreatedReportDTO>(reportService.create(report), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "suspend/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> suspend(@PathVariable UUID id){
        try {
            reportService.suspend(id);
            return new ResponseEntity<>("Report deleted, user suspended.", HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>("Deleting report failed, user not suspended.", HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteReport(@PathVariable UUID id){
        try{
            reportService.delete(id);
            return new ResponseEntity<>("Report deleted.", HttpStatus.NO_CONTENT);

        }catch (Exception e){
            return new ResponseEntity<>("Deleting report failed.", HttpStatus.NOT_FOUND);
        }
    }


}
