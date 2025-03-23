package com.example.payroll.controller;

import com.example.payroll.model.PayrollSummary;
import com.example.payroll.repositories.EmployeeRepository;
import com.example.payroll.service.PayrollService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class PayrollReportController {

    private final EmployeeRepository employeeRepository;
    private final PayrollService payrollService;

    @Autowired
    public PayrollReportController(EmployeeRepository employeeRepository, PayrollService payrollService) {
        this.employeeRepository = employeeRepository;
        this.payrollService = payrollService;
    }

    /**
     * Returns payroll reports for all employees.
     * For each employee, the payroll is calculated dynamically.
     *
     * @return List of PayrollSummary objects in JSON format.
     */
    @GetMapping("/api/payrollreports")
    public List<PayrollSummary> getPayrollReports() {
        return employeeRepository.findAll()
                .stream()
                .map(emp -> payrollService.calculatePayroll(emp.getId()))
                .collect(Collectors.toList());
    }
}
