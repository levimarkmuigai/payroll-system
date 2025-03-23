package com.example.payroll.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tax_band")
public class TaxBand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Lower limit of the tax band
    private double lowerLimit;

    // Upper limit of the tax band
    private double upperLimit;

    // Tax rate for the tax band
    private double taxRate;

    public TaxBand() {}

    public TaxBand(double lowerLimit, double upperLimit, double taxRate) {
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.taxRate = taxRate;
    }   

    // Standard getters and setters

    public Long getId() {
        return id;
    }

    public double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(double upperLimit) {
        this.upperLimit = upperLimit;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }
}
