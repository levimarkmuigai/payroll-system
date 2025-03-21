package com.example.payroll.model;


/**
 * Represents a computed payrol summary for a given employee.
 * This is a DTO (Data Transfer Object) class used to return  calculated payroll data.
 */
public record PayrollSummary(
    Long employeeId,
    String employeeName,
    double basicSalary,
    double grossSalary,
    double nhifDeduction,
    double nssfDeduction,
    double paye,
    double AllowanceConfig
){}