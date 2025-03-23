package com.example.payroll.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * NHIFConfig represents the configuration for NHIF contributions.
 * It now includes lowerBound and upperBound to define the applicable salary range.
 */
@Entity
@Table(name = "nhif_config")
public class NHIFConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The minimum contribution amount required.
    @Column(name = "min_contribution", nullable = false, precision = 10, scale = 2)
    private BigDecimal minContribution;

    // The maximum contribution cap.
    @Column(name = "max_contribution", nullable = false, precision = 10, scale = 2)
    private BigDecimal maxContribution;

    // The rate used for calculating the NHIF contribution.
    @Column(name = "rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal rate;

    // The lower bound of the salary range where this configuration applies.
    @Column(name = "lower_bound", nullable = false, precision = 10, scale = 2)
    private BigDecimal lowerBound;

    // The upper bound of the salary range where this configuration applies.
    @Column(name = "upper_bound", nullable = false, precision = 10, scale = 2)
    private BigDecimal upperBound;

    // Default constructor required by JPA.
    public NHIFConfig() {}

    /**
     * Constructs a new NHIFConfig with all required fields.
     *
     * @param minContribution the minimum contribution amount.
     * @param maxContribution the maximum contribution cap.
     * @param rate the contribution rate.
     * @param lowerBound the lower bound of the applicable salary range.
     * @param upperBound the upper bound of the applicable salary range.
     */
    public NHIFConfig(BigDecimal minContribution, BigDecimal maxContribution, BigDecimal rate,
                      BigDecimal lowerBound, BigDecimal upperBound) {
        this.minContribution = minContribution;
        this.maxContribution = maxContribution;
        this.rate = rate;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    /****** Getters and Setters ******/

    public Long getId() {
        return id;
    }

    public BigDecimal getMinContribution() {
        return minContribution;
    }

    public void setMinContribution(BigDecimal minContribution) {
        this.minContribution = minContribution;
    }

    public BigDecimal getMaxContribution() {
        return maxContribution;
    }

    public void setMaxContribution(BigDecimal maxContribution) {
        this.maxContribution = maxContribution;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(BigDecimal lowerBound) {
        this.lowerBound = lowerBound;
    }

    public BigDecimal getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(BigDecimal upperBound) {
        this.upperBound = upperBound;
    }
}
