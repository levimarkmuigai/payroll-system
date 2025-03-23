package com.example.payroll.model;

import jakarta.persistence.*;

@Entity
@Table(name = "allowance-configs")
public class AllowanceConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Type of allowance
    private String type;

    // Stored as a percentage or fixed value
    private double value;

    // Check if it's a percentage or fixed value
    private boolean isPercentage;

    public AllowanceConfig(){}

    public AllowanceConfig(String type, double value, boolean isPercentage){
        this.type = type;
        this.value = value;
        this.isPercentage = isPercentage;
    }

    // Getters and setters

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    // For type
    public String getType(){
        return type;
    }

    // setter for type
    public void setType(String type){
        this.type = type;
    }

    // For value
    public double getValue(){
        return value;
    }

    public void setValue(double value){
        this.value = value;
    }

    // For isPercentage
    public boolean getIsPercentage(){
        return isPercentage;
    }

    public void setIsPercentage(boolean isPercentage){
        this.isPercentage = isPercentage;
    }
}
