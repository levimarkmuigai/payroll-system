package com.example.payroll.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic payroll fields
    private String name;
    private double basicSalary;

    // New HR fields
    private String department;
    private String jobRole;
    private String email;
    private String phone;
    
    // Track the number of leave days available
    private double leaveBalance;

    // Constructors, getters, and setters

    public Employee() {}

    public Employee(String name, double basicSalary, String department, String jobRole, String email, String phone, double leaveBalance) {
        this.name = name;
        this.basicSalary = basicSalary;
        this.department = department;
        this.jobRole = jobRole;
        this.email = email;
        this.phone = phone;
        this.leaveBalance = leaveBalance;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public double getBasicSalary() {
        return basicSalary;
    }

    public String getDepartment() {
        return department;
    }

    public String getJobRole() {
        return jobRole;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public double getLeaveBalance() {
        return leaveBalance;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLeaveBalance(double leaveBalance) {
        this.leaveBalance = leaveBalance;
    }
}
