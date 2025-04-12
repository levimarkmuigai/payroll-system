package com.example.payroll.controller;

import com.example.payroll.model.Payslip;
import com.example.payroll.service.PayslipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for payslip generation and reporting.
 */
@RestController
@RequestMapping("/api/payslips")
public class PayslipController {

    private final PayslipService payslipService;

    public PayslipController(PayslipService payslipService) {
        this.payslipService = payslipService;
    }

    /**
     * Generates a payslip for a given employee.
     *
     * @param employeeId the ID of the employee.
     * @return the generated Payslip.
     */
    @PostMapping("/generate/{employeeId}")
    public ResponseEntity<Payslip> generatePayslip(@PathVariable Long employeeId) {
        Payslip payslip = payslipService.generatePayslip(employeeId);
        return ResponseEntity.ok(payslip);
    }

    /**
     * Retrieves all generated payslips.
     *
     * @return a list of Payslip objects.
     */
    @GetMapping
    public List<Payslip> getAllPayslips() {
        return payslipService.getAllPayslips();
    }

    /**
     * Endpoint for generating monthly payroll reports.
     * (In a real implementation, you might include query parameters for the
     * month/year.)
     *
     * @return a report (could be in HTML or PDF format) as a string for simplicity.
     */
    @GetMapping("/reports/monthly")
    public ResponseEntity<String> generateMonthlyReport() {
        
        String report = "Monthly Payroll Report\n" +
                "---------------------------\n" +
                "Total Payroll Expense: $XXXXX\n" +
                "Total Deductions: $XXXXX\n" +
                "Breakdown:\n" +
                " - PAYE: $XXXXX\n" +
                " - NHIF: $XXXXX\n" +
                " - NSSF: $XXXXX\n";
        return ResponseEntity.ok(report);
    }
}
