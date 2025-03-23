package com.example.payroll.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Employee full name
    private String name;
    
    // Employee phone number
    private String phoneNumber;
    
    // Employee email address
    private String email;
    
    // Employee gender (e.g., Male, Female, Other)
    private String gender;
    
    // Employee department
    private String department;
    
    // Employee basic salary
    private double basicSalary;

    // Default constructor (required by JPA)
    public Employee() {}

    // Parameterized constructor for convenience
    public Employee(String name, String phoneNumber, String email, String gender, String department, double basicSalary) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.department = department;
        this.basicSalary = basicSalary;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
  
    public String getEmail() {
        return email;
    }
  
    public void setEmail(String email) {
        this.email = email;
    }
  
    public String getGender() {
        return gender;
    }
  
    public void setGender(String gender) {
        this.gender = gender;
    }
  
    public String getDepartment() {
        return department;
    }
  
    public void setDepartment(String department) {
        this.department = department;
    }
  
    public double getBasicSalary() {
        return basicSalary;
    }
  
    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }
}
