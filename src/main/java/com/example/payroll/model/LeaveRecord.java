package com.example.payroll.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Represents a leave record for an employee.
 * Captures leave requests, status, and approval details.
 */
@Entity
@Table(name = "leave_records")
public class LeaveRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many-to-One relationship with Employee
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private com.example.payroll.model.Employee employee;

    // Leave request details
    private LocalDate startDate;
    private LocalDate endDate;
    private String leaveType; // e.g., Annual, Sick, Unpaid
    private String status; // e.g., PENDING, APPROVED, REJECTED

    // Timestamps for request and approval
    private LocalDate requestDate;
    private LocalDate decisionDate;

    public LeaveRecord() {}

    public LeaveRecord(com.example.payroll.model.Employee employee, LocalDate startDate, LocalDate endDate,
                       String leaveType, String status, LocalDate requestDate, LocalDate decisionDate) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveType = leaveType;
        this.status = status;
        this.requestDate = requestDate;
        this.decisionDate = decisionDate;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public com.example.payroll.model.Employee getEmployee() {
        return employee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public LocalDate getDecisionDate() {
        return decisionDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmployee(com.example.payroll.model.Employee employee) {
        this.employee = employee;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public void setDecisionDate(LocalDate decisionDate) {
        this.decisionDate = decisionDate;
    }
}
