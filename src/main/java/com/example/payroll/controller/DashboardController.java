package com.example.payroll.controller;

import com.example.payroll.model.Employee;
import com.example.payroll.model.PayrollSummary;
import com.example.payroll.repositories.EmployeeRepository;
import com.example.payroll.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class DashboardController {

    private final EmployeeRepository employeeRepository;
    private final PayrollService payrollService;

    @Autowired
    public DashboardController(EmployeeRepository employeeRepository, PayrollService payrollService) {
        this.employeeRepository = employeeRepository;
        this.payrollService = payrollService;
    }

    @GetMapping("/api/dashboard")
    public Map<String, Object> getDashboardData() {
        // Fetch all employees from the database
        List<Employee> employees = employeeRepository.findAll();

        int totalEmployees = employees.size();
        double totalPayroll = 0;
        double netSalarySum = 0;

        // Process payroll for each employee and aggregate data
        for (Employee emp : employees) {
            // Call calculatePayroll with the employee's id
            PayrollSummary summary = payrollService.calculatePayroll(emp.getId());
            totalPayroll += summary.grossSalary();
            netSalarySum += summary.netSalary();
        }

        // Build the dynamic dashboard data response
        Map<String, Object> dashboardData = new HashMap<>();
        dashboardData.put("totalEmployees", totalEmployees);
        dashboardData.put("totalPayroll", totalPayroll);
        dashboardData.put("netSalarySum", netSalarySum);
        return dashboardData;
    }
}
