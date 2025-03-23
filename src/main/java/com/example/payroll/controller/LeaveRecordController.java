package com.example.payroll.controller;

import com.example.payroll.model.LeaveRecord;
import com.example.payroll.service.LeaveRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for managing leave requests.
 * Provides endpoints for employees to apply for leave and for HR to approve or reject leave requests.
 */
@RestController
@RequestMapping("/api/leaves")
public class LeaveRecordController {

    private final LeaveRecordService leaveRecordService;

    @Autowired
    public LeaveRecordController(LeaveRecordService leaveService) {
        this.leaveRecordService = leaveService;
    }

    /**
     * Endpoint for an employee to apply for leave.
     *
     * @param employeeId the ID of the employee applying for leave.
     * @param startDate  the start date of the leave in ISO format (yyyy-MM-dd).
     * @param endDate    the end date of the leave in ISO format (yyyy-MM-dd).
     * @param leaveType  the type of leave (e.g., Annual, Sick).
     * @return the created LeaveRecord.
     */
    @PostMapping("/apply")
    public LeaveRecord applyForLeave(@RequestParam Long employeeId,
                                     @RequestParam String startDate,
                                     @RequestParam String endDate,
                                     @RequestParam String leaveType) {
        return leaveRecordService.applyForLeave(employeeId, LocalDate.parse(startDate), LocalDate.parse(endDate), leaveType);
    }

    /**
     * Endpoint for HR to approve a leave request.
     *
     * @param leaveId the ID of the leave request to approve.
     * @return the updated LeaveRecord.
     */
    @PutMapping("/approve/{leaveId}")
    public ResponseEntity<LeaveRecord> approveLeave(@PathVariable Long leaveId) {
        try {
            LeaveRecord leaveRecord = leaveRecordService.approveLeave(leaveId);
            return ResponseEntity.ok(leaveRecord);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint for HR to reject a leave request.
     *
     * @param leaveId the ID of the leave request to reject.
     * @return the updated LeaveRecord.
     */
    @PutMapping("/reject/{leaveId}")
    public ResponseEntity<LeaveRecord> rejectLeave(@PathVariable Long leaveId) {
        try {
            LeaveRecord leaveRecord = leaveRecordService.rejectLeave(leaveId);
            return ResponseEntity.ok(leaveRecord);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all leave records.
     *
     * @return a list of LeaveRecord objects.
     */
    @GetMapping
    public List<LeaveRecord> getAllLeaveRecords() {
        return leaveRecordService.getAllLeaveRecords();
    }
}
