package com.ftn.event_hopper.controllers.reports;

import com.ftn.event_hopper.dtos.reports.CreateReportDTO;
import com.ftn.event_hopper.dtos.reports.CreatedReportDTO;
import com.ftn.event_hopper.dtos.reports.GetReportDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetReportDTO>> getReports(){

        Collection<GetReportDTO> reports = new ArrayList<GetReportDTO>();

        return new ResponseEntity<Collection<GetReportDTO>>(reports, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetReportDTO> getReport(@PathVariable UUID id){

        GetReportDTO report = new GetReportDTO();

        if (report == null) {
            return new ResponseEntity<GetReportDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<GetReportDTO>(report,HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedReportDTO> createReport(@RequestBody CreateReportDTO report){
        CreatedReportDTO createdReport = new CreatedReportDTO();

        return new ResponseEntity<CreatedReportDTO>(createdReport, HttpStatus.CREATED);
    }


}
