package com.example.payroll.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Payslip entity stores historical payroll data for each payroll cycle.
 */
@Entity
@Table(name = "payslips")
public class Payslip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The employee for whom the payslip is generated
    private Long employeeId;

    // Employee name for convenience
    private String employeeName;

    // Payroll details captured in the payslip
    private double basicSalary;
    private double allowances;
    private double grossSalary;
    private double paye;
    private double nhifDeduction;
    private double nssfDeduction;
    private double totalDeductions;
    private double netSalary;

    // Metadata
    private LocalDateTime generationDate;

    public Payslip() {}

    public Payslip(Long employeeId, String employeeName, double basicSalary, double allowances,
                   double grossSalary, double paye, double nhifDeduction, double nssfDeduction,
                   double totalDeductions, double netSalary, LocalDateTime generationDate) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.basicSalary = basicSalary;
        this.allowances = allowances;
        this.grossSalary = grossSalary;
        this.paye = paye;
        this.nhifDeduction = nhifDeduction;
        this.nssfDeduction = nssfDeduction;
        this.totalDeductions = totalDeductions;
        this.netSalary = netSalary;
        this.generationDate = generationDate;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public double getAllowances() {
        return allowances;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public double getPaye() {
        return paye;
    }

    public double getNhifDeduction() {
        return nhifDeduction;
    }

    public double getNssfDeduction() {
        return nssfDeduction;
    }

    public double getTotalDeductions() {
        return totalDeductions;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public LocalDateTime getGenerationDate() {
        return generationDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public void setAllowances(double allowances) {
        this.allowances = allowances;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public void setPaye(double paye) {
        this.paye = paye;
    }

    public void setNhifDeduction(double nhifDeduction) {
        this.nhifDeduction = nhifDeduction;
    }

    public void setNssfDeduction(double nssfDeduction) {
        this.nssfDeduction = nssfDeduction;
    }

    public void setTotalDeductions(double totalDeductions) {
        this.totalDeductions = totalDeductions;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public void setGenerationDate(LocalDateTime generationDate) {
        this.generationDate = generationDate;
    }
}
