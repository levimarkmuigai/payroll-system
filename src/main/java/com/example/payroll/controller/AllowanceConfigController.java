package com.example.payroll.controller;

import com.example.payroll.model.AllowanceConfig;
import com.example.payroll.repositories.AllownanceConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing Allowance configurations.
 * Provides endpoints to create, read, update, and delete allowance configuration data.
 */
@RestController
@RequestMapping("/api/allowance-config")
public class AllowanceConfigController {

    private final AllownanceConfigRepository allowanceConfigRepository;

    /**
     * Constructor injection for AllownanceConfigRepository.
     *
     * @param allowanceConfigRepository Repository for Allowance configuration data.
     */
    @Autowired
    public AllowanceConfigController(AllownanceConfigRepository allowanceConfigRepository) {
        this.allowanceConfigRepository = allowanceConfigRepository;
    }

    /**
     * Retrieves all allowance configurations.
     *
     * @return a list of AllowanceConfig objects.
     */
    @GetMapping
    public List<AllowanceConfig> getAllAllowanceConfigs() {
        return allowanceConfigRepository.findAll();
    }

    /**
     * Retrieves a specific Allowance configuration by its id.
     *
     * @param id the unique identifier of the Allowance configuration.
     * @return the AllowanceConfig wrapped in a ResponseEntity, or 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AllowanceConfig> getAllowanceConfigById(@PathVariable Long id) {
        return allowanceConfigRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new Allowance configuration.
     *
     * @param allowanceConfig the Allowance configuration to create.
     * @return the created AllowanceConfig object.
     */
    @PostMapping
    public AllowanceConfig createAllowanceConfig(@RequestBody AllowanceConfig allowanceConfig) {
        return allowanceConfigRepository.save(allowanceConfig);
    }

    /**
     * Updates an existing Allowance configuration.
     *
     * @param id the id of the Allowance configuration to update.
     * @param updatedAllowanceConfig the updated allowance configuration data.
     * @return the updated AllowanceConfig wrapped in a ResponseEntity, or 404 if not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AllowanceConfig> updateAllowanceConfig(@PathVariable Long id, @RequestBody AllowanceConfig updatedAllowanceConfig) {
        return allowanceConfigRepository.findById(id)
                .map(existingConfig -> {
                    existingConfig.setType(updatedAllowanceConfig.getType());
                    existingConfig.setValue(updatedAllowanceConfig.getValue());
                    existingConfig.setIsPercentage(updatedAllowanceConfig.getIsPercentage());
                    AllowanceConfig savedConfig = allowanceConfigRepository.save(existingConfig);
                    return ResponseEntity.ok(savedConfig);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deletes an Allowance configuration by its id.
     *
     * @param id the id of the Allowance configuration to delete.
     * @return a ResponseEntity with HTTP status 204 (No Content) if deletion is successful,
     *         or 404 if the configuration is not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAllowanceConfig(@PathVariable Long id) {
        return allowanceConfigRepository.findById(id)
            .map(existingConfig -> {
                allowanceConfigRepository.delete(existingConfig);
                return ResponseEntity.noContent().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
