package com.example.payroll.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "nhif_config")
public class NHIFConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "min_contribution", nullable = false, precision = 10, scale = 2)
    private BigDecimal minContribution;

    @Column(name = "max_contribution", nullable = false, precision = 10, scale = 2) 
    private BigDecimal maxContribution;

    @Column(name = "rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal rate;

    // Default constructor required by JPA (Java Persistence API)
    public NHIFConfig() {}

    public NHIFConfig(BigDecimal minContribution, BigDecimal maxContribution, BigDecimal rate){
        this.minContribution = minContribution;
        this.maxContribution = maxContribution;
        this.rate = rate;
    }

    /******Getters and Setters******/

    public Long getId() {
        return id;
    }

    public BigDecimal getMinContribution(){
        return minContribution;
    }

    public void setMinContribution(BigDecimal minContribution){
        this.minContribution = minContribution;
    }

    public BigDecimal getMaxContribution(){
        return maxContribution;
    }

    public void setMaxContribution(BigDecimal maxContribution){
        this.maxContribution = maxContribution;
    }

    public BigDecimal getRate(){
        return rate;
    }

    public void setRate(BigDecimal rate){
        this.rate = rate;
    }
}