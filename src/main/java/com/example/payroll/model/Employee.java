package com.example.payroll.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity                   
@Table(name = "employees") 
public class Employee {

    @Id                                    
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generates unique IDs
    private Long id;

    private String name;
    
    private double basicSalary;

    private String department;
    
    private String email;

    private String phoneNumber;

    private String jobTitle;

    private LocalDate dateJoined;

    private int leaveBalance;

    // Default constructor is required by JPA
    public Employee() {}

    // Parameterized constructor for easier object creation
    public Employee(String name, double basicSalary, String department, String email, String phoneNumber, String jobTitle, LocalDate dateJoined, int leaveBalance) {
        this.name = name;
        this.basicSalary = basicSalary;
        this.department = department;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.jobTitle = jobTitle;
        this.dateJoined = dateJoined;
        this.leaveBalance = leaveBalance; 
    }

    // Getters and Setters for each field

    // For the ID
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // For the Employee name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //For employee basic salary
    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    // For employee department
    public String getDepartment(){
        return department;
    }

    public void setDepartment(String department){
        this.department = department;
    }

    // For employee email
    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    // For employee Phone number
    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    // For employee Job Title
    public String getJobTitle(){
        return jobTitle;
    }

    public void setJobTitle(String jobTitle){
        this.jobTitle = jobTitle;
    }

    // For Employees date Joined
    public LocalDate getDateJoined(){
        return dateJoined;
    }

    public void setDateJoined(LocalDate dateJoined){
        this.dateJoined = dateJoined;
    }

    // For Employees leave date 
    public int getLeaveBalance(){
        return leaveBalance;
    }

    public void setLeaveBalance(int leaveBalance){
        this.leaveBalance =leaveBalance;
    }
}
