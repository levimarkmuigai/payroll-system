package com.example.payroll.controller;

import com.example.payroll.model.NSSFConfig;
import com.example.payroll.repositories.NSSFConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing NSSF configurations.
 * Provides endpoints to create, read, update, and delete NSSF configuration data.
 */
@RestController
@RequestMapping("/api/nssf-config")
public class NSSFConfigController {

    private final NSSFConfigRepository nssfConfigRepository;

    /**
     * Constructor injection for NSSFConfigRepository.
     *
     * @param nssfConfigRepository Repository for NSSF configuration data.
     */
    @Autowired
    public NSSFConfigController(NSSFConfigRepository nssfConfigRepository) {
        this.nssfConfigRepository = nssfConfigRepository;
    }

    /**
     * Retrieves all NSSF configurations.
     *
     * @return a list of NSSFConfig objects.
     */
    @GetMapping
    public List<NSSFConfig> getAllNSSFConfigs() {
        return nssfConfigRepository.findAll();
    }

    /**
     * Retrieves a specific NSSF configuration by its id.
     *
     * @param id the unique identifier of the NSSF configuration.
     * @return the NSSFConfig wrapped in a ResponseEntity, or 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NSSFConfig> getNSSFConfigById(@PathVariable Long id) {
        return nssfConfigRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new NSSF configuration.
     *
     * @param nssfConfig the NSSF configuration to create.
     * @return the created NSSFConfig object.
     */
    @PostMapping
    public NSSFConfig createNSSFConfig(@RequestBody NSSFConfig nssfConfig) {
        return nssfConfigRepository.save(nssfConfig);
    }

    /**
     * Updates an existing NSSF configuration.
     *
     * @param id the id of the NSSF configuration to update.
     * @param updatedNSSFConfig the updated NSSF configuration data.
     * @return the updated NSSFConfig wrapped in a ResponseEntity, or 404 if not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NSSFConfig> updateNSSFConfig(@PathVariable Long id, @RequestBody NSSFConfig updatedNSSFConfig) {
        return nssfConfigRepository.findById(id)
            .map(existingConfig -> {
                // Update NSSF configuration fields.
                existingConfig.setTierILimit(updatedNSSFConfig.getTierILimit());
                existingConfig.setTierIILimit(updatedNSSFConfig.getTierIILimit());
                existingConfig.setTierIIRate(updatedNSSFConfig.getTierIIRate());
                NSSFConfig savedConfig = nssfConfigRepository.save(existingConfig);
                return ResponseEntity.ok(savedConfig);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    /**
    * Deletes an NSSF configuration by its id.
    *
    * @param id the id of the NSSF configuration to delete.
    * @return a ResponseEntity with HTTP status 204 (No Content) if deletion is successful,
    *         or 404 if the configuration is not found.
    */
   
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNSSFConfig(@PathVariable Long id) {
        return nssfConfigRepository.findById(id)
            .map(nssfConfig -> {
                nssfConfigRepository.delete(nssfConfig);
                return ResponseEntity.noContent().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
