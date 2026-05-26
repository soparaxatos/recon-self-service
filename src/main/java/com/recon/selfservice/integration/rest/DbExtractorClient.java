package com.recon.selfservice.integration.rest;

import com.recon.selfservice.model.dto.DBSParameterDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "db-extractor")
public interface DbExtractorClient {

    @GetMapping("/dbs-parameter/")
    ResponseEntity<List<DBSParameterDTO>> getAllParameters(@RequestParam("size") int size, @RequestHeader("Authorization") String token);

    @GetMapping("/dbs-parameter/{id}")
    ResponseEntity<DBSParameterDTO> getParameterById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);

    @PostMapping("/dbs-parameter/")
    ResponseEntity<DBSParameterDTO> createParameter(@RequestBody DBSParameterDTO parameter, @RequestHeader("Authorization") String token);

    @PutMapping("/dbs-parameter/{id}")
    ResponseEntity<DBSParameterDTO> updateParameter(@PathVariable("id") Long id, @RequestBody DBSParameterDTO parameter, @RequestHeader("Authorization") String token);
}
