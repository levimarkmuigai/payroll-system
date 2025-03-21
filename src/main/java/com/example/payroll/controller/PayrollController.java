package com.example.payroll.controller;


import com.example.payroll.model.PayrollSummary;
import com.example.payroll.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * PayrollController exposes REST endpoints related to payroll processing.
 * It delegates payroll calculations to the PayrollService.
 */

@RestController
@RequestMapping("/api/payroll")
public class PayrollController {
    
    private final PayrollService payrollService;

     /**
     * Constructor injection for PayrollService.
     *
     * @param payrollService the service responsible for payroll calculations.
     */

    @Autowired
    public PayrollController(PayrollService payrollService){
        this.payrollService = payrollService;
    }

    /**
     * Endpoint to calculate the payroll summary for a given employee.
     * 
     * Example: GET /api/payroll/123
     *
     * @param employeeId the unique identifier of the employee.
     * @return a PayrollSummary DTO containing payroll details.
    */
    @GetMapping("/{employeeId}")
    public PayrollSummary getPayrollSummary(@PathVariable Long employeeId){
        return payrollService.calculatePayroll(employeeId);
    }
}
