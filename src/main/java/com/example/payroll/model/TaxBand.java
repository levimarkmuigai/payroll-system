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

    // *****Getters and Setters *****

    public Long getId() {
        return id;
    }

    public double getlowerLimit(){
        return lowerLimit;
    }

    public void setlowerLimit(double lowerLimit){
        this.lowerLimit = lowerLimit;
    }

    public double getupperLimit(){
        return upperLimit;
    }

    public void setupperLimit(double upperLimit){
        this.upperLimit = upperLimit;
    }

    public double gettaxRate(){
        return taxRate;
    }

    public void settaxRate(double taxRate){
        this.taxRate = taxRate;
    }
}
