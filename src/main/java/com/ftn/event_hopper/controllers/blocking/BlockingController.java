package com.ftn.event_hopper.controllers.blocking;


import com.ftn.event_hopper.dtos.blocking.CreateBlockDTO;
import com.ftn.event_hopper.dtos.blocking.CreatedBlockDTO;
import com.ftn.event_hopper.dtos.blocking.GetBlockDTO;
import com.ftn.event_hopper.services.blocking.BlockingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/blocking")
public class BlockingController {
    @Autowired
    private BlockingService blockingService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetBlockDTO>> getReports(){

        Collection<GetBlockDTO> reports = blockingService.findAll();
        if(reports == null) {
            return new ResponseEntity<Collection<GetBlockDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetBlockDTO> getReport(@PathVariable UUID id){

        GetBlockDTO report = blockingService.findById(id);

        if (report == null) {
            return new ResponseEntity<GetBlockDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(report,HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedBlockDTO> createReport(@RequestBody CreateBlockDTO report){
        return new ResponseEntity<CreatedBlockDTO>(blockingService.create(report), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteReport(@PathVariable UUID id){
        try{
            blockingService.delete(id);
            return new ResponseEntity<>("Block deleted.", HttpStatus.NO_CONTENT);

        }catch (Exception e){
            return new ResponseEntity<>("Deleting block failed.", HttpStatus.NOT_FOUND);
        }
    }


}
