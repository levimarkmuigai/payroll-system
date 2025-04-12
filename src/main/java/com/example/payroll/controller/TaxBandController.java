package com.example.payroll.controller;

import com.example.payroll.model.TaxBand;
import com.example.payroll.repositories.TaxBandRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing TaxBand configurations.
 * Provides endpoints to create, read, update, and delete tax band
 * configurations.
 */
@RestController
@RequestMapping("/api/taxbands")
public class TaxBandController {

    private final TaxBandRepository taxBandRepository;

    public TaxBandController(TaxBandRepository taxBandRepository) {
        this.taxBandRepository = taxBandRepository;
    }

    /**
     * Retrieves all tax band configurations.
     *
     * @return a list of TaxBand objects.
     */
    @GetMapping
    public List<TaxBand> getAllTaxBands() {
        return taxBandRepository.findAll();
    }

    /**
     * Retrieves a specific TaxBand by its id.
     *
     * @param id the unique identifier of the TaxBand.
     * @return the TaxBand configuration wrapped in a ResponseEntity.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaxBand> getTaxBand(@PathVariable Long id) {
        return taxBandRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new TaxBand configuration.
     *
     * @param taxBand the TaxBand object to create.
     * @return the created TaxBand configuration.
     */
    @PostMapping
    public TaxBand createTaxBand(@RequestBody TaxBand taxBand) {
        return taxBandRepository.save(taxBand);
    }

    /**
     * Updates an existing TaxBand configuration.
     *
     * @param id             the id of the TaxBand to update.
     * @param updatedTaxBand the updated TaxBand data.
     * @return the updated TaxBand configuration wrapped in a ResponseEntity.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaxBand> updateTaxBand(@PathVariable Long id, @RequestBody TaxBand updatedTaxBand) {
        return taxBandRepository.findById(id)
                .map(existingTaxBand -> {
                    existingTaxBand.setLowerLimit(updatedTaxBand.getLowerLimit());
                    existingTaxBand.setUpperLimit(updatedTaxBand.getUpperLimit());
                    existingTaxBand.setTaxRate(updatedTaxBand.getTaxRate());
                    TaxBand savedTaxBand = taxBandRepository.save(existingTaxBand);
                    return ResponseEntity.ok(savedTaxBand);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deletes a TaxBand configuration by its id.
     *
     * @param id the id of the TaxBand to delete.
     * @return a ResponseEntity with HTTP status 204 (No Content) if deletion is
     *         successful.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaxBand(@PathVariable Long id) {
        if (taxBandRepository.existsById(id)) {
            taxBandRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
