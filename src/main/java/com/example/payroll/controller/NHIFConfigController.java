package com.example.payroll.controller;

import com.example.payroll.model.NHIFConfig;
import com.example.payroll.repositories.NHIFConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST controller for managing NHIF configurations.
 * Provides endpoints to create, read, update, and delete NHIF configuration data.
 */

@RestController
@RequestMapping("/api/nhif-config")
public class NHIFConfigController {
    private final NHIFConfigRepository nhifConfigRepository;

    /**
    * Constructor injection for NHIFConfigRepository.
    *
    * @param nhifConfigRepository Repository for NHIF configuration data.
    */

    @Autowired
    public NHIFConfigController(NHIFConfigRepository nhifConfigRepository) {
        this.nhifConfigRepository = nhifConfigRepository;
    }

    /**
    * Retrieves all NHIF configurations.
    *
    * @return a list of NHIFConfig objects.
    */
    @GetMapping
    public List<NHIFConfig> getAllNHIFConfigs() {
        return nhifConfigRepository.findAll();
    }

    /**
    * Retrieves a specific NHIF configuration by its id.
    *
    * @param id the unique identifier of the NHIF configuration.
    * @return the NHIFConfig wrapped in a ResponseEntity, or 404 if not found.
    */
    @GetMapping("/{id}")
    public ResponseEntity<NHIFConfig> getNHIFConfigById(@PathVariable Long id) {
        return nhifConfigRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
    * Creates a new NHIF configuration.
    *
    * @param nhifConfig the NHIF configuration to create.
    * @return the created NHIFConfig object.
    */
    @PostMapping
    public NHIFConfig createNhifConfig(@RequestBody NHIFConfig nhifconfig){
        return nhifConfigRepository.save(nhifconfig);
    }

    /**
    * Updates an existing NHIF configuration.
    *
    * @param id the id of the NHIF configuration to update.
    * @param updatedNHIFConfig the updated NHIF configuration data.
    * @return the updated NHIFConfig wrapped in a ResponseEntity, or 404 if not found.
    */
   
    
    /**
    * Deletes an NHIF configuration by its id.
    * @param id the id of the NHIF configuration to delete.
    * @return a ResponseEntity with HTTP status 204 (No Content) if deletion is successful,
    *         or 404 if the configuration is not found.
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNHIFConfig(@PathVariable Long id) {
        return nhifConfigRepository.findById(id)
            .map(nhifConfig -> {
                nhifConfigRepository.delete(nhifConfig);
                return ResponseEntity.noContent().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }


    

}
