package com.example.payroll.model;

import jakarta.persistence.*;

@Entity                   
@Table(name = "employees") 
public class Employee {

    @Id                                    
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generates unique IDs
    private Long id;

    private String name;
    
    private double basicSalary;

    // Default constructor is required by JPA
    public Employee() {}

    // Parameterized constructor for easier object creation
    public Employee(String name, double basicSalary) {
        this.name = name;
        this.basicSalary = basicSalary;
    }

    // Getters and Setters for each field

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }
}
