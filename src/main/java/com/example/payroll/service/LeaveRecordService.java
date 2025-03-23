package com.example.payroll.service;

import com.example.payroll.model.LeaveRecord;
import com.example.payroll.repositories.LeaveRecordRepository;
import com.example.payroll.model.Employee;
import com.example.payroll.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LeaveRecordService {

    private final LeaveRecordRepository leaveRecordRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public LeaveRecordService(LeaveRecordRepository leaveRecordRepository, EmployeeRepository employeeRepository) {
        this.leaveRecordRepository = leaveRecordRepository;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Allows an employee to apply for leave.
     *
     * @param employeeId the ID of the employee applying for leave.
     * @param startDate  the start date of the leave.
     * @param endDate    the end date of the leave.
     * @param leaveType  the type of leave (e.g., Annual, Sick).
     * @return the created LeaveRecord.
     */
    public LeaveRecord applyForLeave(Long employeeId, LocalDate startDate, LocalDate endDate, String leaveType) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        LeaveRecord leaveRecord = new LeaveRecord(employee, startDate, endDate, leaveType, "PENDING", LocalDate.now(), null);
        return leaveRecordRepository.save(leaveRecord);
    }

    /**
     * Approves a leave request.
     *
     * @param leaveId the ID of the leave request to approve.
     * @return the updated LeaveRecord.
     */
    public LeaveRecord approveLeave(Long leaveId) {
        LeaveRecord leaveRecord = leaveRecordRepository.findById(leaveId)
                .orElseThrow(() -> new IllegalArgumentException("Leave record not found"));
        leaveRecord.setStatus("APPROVED");
        leaveRecord.setDecisionDate(LocalDate.now());
        // Optionally update the employee's leave balance here.
        return leaveRecordRepository.save(leaveRecord);
    }

    /**
     * Rejects a leave request.
     *
     * @param leaveId the ID of the leave request to reject.
     * @return the updated LeaveRecord.
     */
    public LeaveRecord rejectLeave(Long leaveId) {
        LeaveRecord leaveRecord = leaveRecordRepository.findById(leaveId)
                .orElseThrow(() -> new IllegalArgumentException("Leave record not found"));
        leaveRecord.setStatus("REJECTED");
        leaveRecord.setDecisionDate(LocalDate.now());
        return leaveRecordRepository.save(leaveRecord);
    }

    /**
     * Retrieves all leave records.
     *
     * @return a list of LeaveRecord objects.
     */
    public List<LeaveRecord> getAllLeaveRecords() {
        return leaveRecordRepository.findAll();
    }
}
