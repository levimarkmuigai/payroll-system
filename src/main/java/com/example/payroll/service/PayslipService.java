package com.example.payroll.service;


import com.example.payroll.model.Payslip;
import com.example.payroll.model.PayrollSummary;
import com.example.payroll.repositories.PayslipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PayslipService {

    private final PayrollService payrollService;
    private final PayslipRepository payslipRepository;

    @Autowired
    public PayslipService(PayrollService payrollService, PayslipRepository payslipRepository) {
        this.payrollService = payrollService;
        this.payslipRepository = payslipRepository;
    }

    /**
     * Generates a payslip for an employee for the current payroll cycle.
     * The payroll details are retrieved from the PayrollService.
     *
     * @param employeeId the ID of the employee.
     * @return the generated Payslip.
     */
    public Payslip generatePayslip(Long employeeId) {
        // Retrieve payroll summary
        PayrollSummary summary = payrollService.calculatePayroll(employeeId);

        // Calculate total deductions (PAYE + NHIF + NSSF)
        double totalDeductions = summary.paye() + summary.nhifDeduction() + summary.nssfDeduction();

        // Create a Payslip with all 11 required parameters.
        Payslip payslip = new Payslip(
            employeeId,                // employeeId
            summary.employeeName(),    // employeeName
            summary.basicSalary(),     // basicSalary
            summary.allowances(),      // allowances
            summary.grossSalary(),     // grossSalary
            summary.paye(),            // paye
            summary.nhifDeduction(),   // nhifDeduction
            summary.nssfDeduction(),   // nssfDeduction
            totalDeductions,           // totalDeductions
            summary.netSalary(),       // netSalary
            LocalDateTime.now()        // generationDate
        );
        return payslipRepository.save(payslip);
    }

    /**
     * Retrieves all generated payslips.
     *
     * @return a list of Payslip objects.
     */
    public List<Payslip> getAllPayslips() {
        return payslipRepository.findAll();
    }
}
