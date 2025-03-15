package com.example.payroll.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "nssf_config")
public class NSSFConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tier I Rate and Limit
    @Column(name = "tier_I_Rate", nullable = false, precision = 5, scale = 4)
    private BigDecimal tierIRate;
   
    @Column(name = "tier_I_Limit", nullable = false, precision = 10, scale = 2)
    private BigDecimal tierILimit;

    // Tier II Rate and Limit
    @Column(name = "tier_II_Rate", nullable = false, precision = 5, scale = 4)
    private BigDecimal tierIIRate;

    @Column(name = "tier_II_Limit", nullable = false, precision = 10, scale = 2)
    private BigDecimal tierIILimit;

    // Constructor for JpaRespository
    public NSSFConfig(){}

    public NSSFConfig(BigDecimal tierIRate, BigDecimal tierILimit, BigDecimal tierIIRate, BigDecimal tierIILimit){
        this.tierIRate = tierIRate;
        this.tierIIRate = tierIIRate;
        this.tierILimit = tierILimit;
        this.tierIILimit = tierIILimit;
    }

    /******Getters and Setters*******/
    public Long getId(){
        return id;
    }

    public BigDecimal getTierIRate(){
        return tierIRate;
    }

    public void setTierIRate(BigDecimal tierIRate){
        this.tierIRate = tierIRate;
    }

    public BigDecimal getTierIIRate(){
        return tierIIRate;
    }

    public void setTierIIRate(BigDecimal tierIIRate){
        this.tierIIRate = tierIIRate;
    }

    public BigDecimal getTierILimit(){
        return tierILimit;
    }

    public void setTierILimit(BigDecimal tierILimit){
        this.tierILimit = tierILimit;
    }

    public BigDecimal getTierIILimit(){
        return tierIILimit;
    }

    public void setTierIILimit(BigDecimal tierIILimit){
        this.tierIILimit = tierIILimit;
    }

    @Override
    public String toString(){
        return "NSSFconfig{" +
        "id=" + id +
        ", tierIRate=" + tierIRate +
        ", tierILimit=" + tierILimit +
        ", tierIIRate=" + tierIIRate +
        ", tierIILimit=" + tierIILimit +
        '}';
    }
}
