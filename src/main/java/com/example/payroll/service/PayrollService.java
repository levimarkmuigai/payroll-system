package com.example.payroll.service;

import com.example.payroll.model.Employee;
import com.example.payroll.model.NHIFConfig;
import com.example.payroll.model.AllowanceConfig;
import com.example.payroll.model.NSSFConfig;
import com.example.payroll.model.TaxBand;
import com.example.payroll.model.PayrollSummary;
import com.example.payroll.repositories.EmployeeRepository;
import com.example.payroll.repositories.AllownanceConfigRepository;
import com.example.payroll.repositories.NHIFConfigRepository;
import com.example.payroll.repositories.NSSFConfigRepository;
import com.example.payroll.repositories.TaxBandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PayrollService {

    private final EmployeeRepository employeeRepository;
    private final AllownanceConfigRepository allownanceConfigRepository;
    private final NHIFConfigRepository nhifConfigRepository;
    private final NSSFConfigRepository nssfConfigRepository;
    private final TaxBandRepository taxBandRepository;

    /**
     * Constructor injection ensures that all required dependencies for payroll processing are available.
     *
     * @param employeeRepository        Repository for employee data.
     * @param nhifConfigRepository      Repository for NHIF configurations.
     * @param nssfConfigRepository      Repository for NSSF configurations.
     * @param taxBandRepository         Repository for Tax Band configurations.
     * @param allowanceConfigRepository Repository for Allowance configurations.
     */
    @Autowired
    public PayrollService(EmployeeRepository employeeRepository, NHIFConfigRepository nhifConfigRepository,
                          NSSFConfigRepository nssfConfigRepository, TaxBandRepository taxBandRepository,
                          AllownanceConfigRepository allowanceConfigRepository) {
        this.employeeRepository = employeeRepository;
        this.nhifConfigRepository = nhifConfigRepository;
        this.nssfConfigRepository = nssfConfigRepository;
        this.taxBandRepository = taxBandRepository;
        this.allownanceConfigRepository = allowanceConfigRepository;
    }

    /**
     * Calculates the payroll for a given employee and returns a PayrollSummary DTO.
     * 
     * @param employeeId The ID of the employee for whom the payroll is to be calculated.
     * @return PayrollSummary DTO containing the payroll details.
     */
    public PayrollSummary calculatePayroll(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        double basicSalary = employee.getBasicSalary();
        double allowances = calculateTotalAllowance(employee);
        double grossSalary = basicSalary + allowances;
        double paye = calculatePAYE(employee);
        double nhif = calculateNHIF(employee);
        double nssf = calculateNSSF(employee);
        double totalDeductions = paye + nhif + nssf;
        double netSalary = grossSalary - totalDeductions;

        // Adjust the constructor parameters as needed.
        // Assuming PayrollSummary has the following constructor:
        // PayrollSummary(Long employeeId, String employeeName, double basicSalary,
        //                double grossSalary, double nhifDeduction, double nssfDeduction,
        //                double paye, double netSalary, double allowanceConfig)
        return new PayrollSummary(
            employee.getId(),      // employeeId
            employee.getName(),    // employeeName
            basicSalary,           // basicSalary
            grossSalary,           // grossSalary
            nhif,                  // nhifDeduction
            nssf,                  // nssfDeduction
            paye,                  // paye
            netSalary,             // netSalary  
            allowances             // allowanceConfig (total allowances)
        );
    }

    /**
     * Calculates the PAYE (income tax) based on the employee's basic salary and tax bands.
     *
     * @param employee the Employee whose tax is to be calculated.
     * @return the calculated PAYE amount.
     */
    public double calculatePAYE(Employee employee) {
        double basicSalary = employee.getBasicSalary();
        List<TaxBand> taxBands = taxBandRepository.findAll();
        double taxDue = 0;

        // Sort the tax bands by lower limit for progressive calculation.
        taxBands.sort((a, b) -> Double.compare(a.getlowerLimit(), b.getlowerLimit()));

        double remainingSalary = basicSalary;
        for (TaxBand band : taxBands) {
            if (remainingSalary <= 0) break;
            double taxableInBand = Math.min(remainingSalary, band.getupperLimit() - band.getlowerLimit());
            taxDue += taxableInBand * (band.gettaxRate() / 100);
            remainingSalary -= taxableInBand;
        }
        return taxDue;
    }

    /**
     * Calculates the NHIF deduction based on the employee's basic salary and the applicable NHIF configuration.
     * This version uses the lowerBound and upperBound to select the correct configuration.
     *
     * @param employee the Employee whose NHIF deduction is to be calculated.
     * @return the calculated NHIF deduction amount.
     */
    public double calculateNHIF(Employee employee) {
        double basicSalary = employee.getBasicSalary();
        BigDecimal basicSalaryBD = BigDecimal.valueOf(basicSalary);
        
        // Find the NHIF configuration where the basic salary is within the configured bounds.
        Optional<NHIFConfig> configOpt = nhifConfigRepository.findAll().stream()
            .filter(config -> basicSalaryBD.compareTo(config.getLowerBound()) >= 0 &&
                              basicSalaryBD.compareTo(config.getUpperBound()) <= 0)
            .findFirst();
        
        NHIFConfig config = configOpt.orElseGet(() -> nhifConfigRepository.findAll().stream().findFirst()
            .orElse(new NHIFConfig(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)));
        
        double nhifDeduction = basicSalary * (config.getRate().doubleValue() / 100);

        // Enforce minimum contribution.
        if (nhifDeduction < config.getMinContribution().doubleValue()) {
            nhifDeduction = config.getMinContribution().doubleValue();
        }
        // Enforce maximum contribution.
        if (nhifDeduction > config.getMaxContribution().doubleValue()) {
            nhifDeduction = config.getMaxContribution().doubleValue();
        }
        return nhifDeduction;
    }

    /**
     * Calculates the NSSF deduction based on the employee's basic salary and NSSF configuration.
     *
     * @param employee the Employee whose NSSF deduction is to be calculated.
     * @return the calculated NSSF deduction amount.
     */
    public double calculateNSSF(Employee employee) {
        NSSFConfig nssfConfig = nssfConfigRepository.findAll().stream().findFirst().orElse(new NSSFConfig());
        double basicSalary = employee.getBasicSalary();

        // Calculation Logic using the NSSF tier
        double tier1Contribution = Math.min(basicSalary, nssfConfig.getTierILimit().doubleValue());
        double tier2Base = Math.min(Math.max(basicSalary - nssfConfig.getTierIILimit().doubleValue(), 0),
                                    nssfConfig.getTierIILimit().doubleValue());
        double tier2Contribution = tier2Base * (nssfConfig.getTierIIRate().doubleValue() / 100);
        return tier1Contribution + tier2Contribution;
    }

    /**
     * Calculates the total allowance of the employee based on the configured allowances.
     *
     * @param employee the Employee whose total allowance is to be calculated.
     * @return the calculated total allowance amount.
     */
    public double calculateTotalAllowance(Employee employee) {
        double basicSalary = employee.getBasicSalary();
        double totalAllowance = 0;

        List<AllowanceConfig> allowances = allownanceConfigRepository.findAll();
        for (AllowanceConfig allowance : allowances) {
            if (allowance.getIsPercentage()) {
                totalAllowance += basicSalary * (allowance.getValue() / 100);
            } else {
                totalAllowance += allowance.getValue();
            }
        }
        return totalAllowance;
    }
}
