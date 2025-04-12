package com.example.payroll.service;

import com.example.payroll.model.Employee;
import com.example.payroll.model.NHIFConfig;
import com.example.payroll.model.NSSFConfig;
import com.example.payroll.model.TaxBand;
import com.example.payroll.model.AllowanceConfig;
import com.example.payroll.model.PayrollSummary;
import com.example.payroll.repositories.EmployeeRepository;
import com.example.payroll.repositories.AllownanceConfigRepository;
import com.example.payroll.repositories.NHIFConfigRepository;
import com.example.payroll.repositories.NSSFConfigRepository;
import com.example.payroll.repositories.TaxBandRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PayrollService {

    private final EmployeeRepository employeeRepository;
    private final AllownanceConfigRepository allowanceConfigRepository;
    private final NHIFConfigRepository nhifConfigRepository;
    private final NSSFConfigRepository nssfConfigRepository;
    private final TaxBandRepository taxBandRepository;

    /**
     * Constructor injection ensures that all required dependencies for payroll
     * processing are available.
     *
     * @param employeeRepository        Repository for employee data.
     * @param nhifConfigRepository      Repository for NHIF configurations.
     * @param nssfConfigRepository      Repository for NSSF configurations.
     * @param taxBandRepository         Repository for Tax Band configurations.
     * @param allowanceConfigRepository Repository for Allowance configurations.
     */
    public PayrollService(EmployeeRepository employeeRepository,
            NHIFConfigRepository nhifConfigRepository,
            NSSFConfigRepository nssfConfigRepository,
            TaxBandRepository taxBandRepository,
            AllownanceConfigRepository allowanceConfigRepository) {
        this.employeeRepository = employeeRepository;
        this.nhifConfigRepository = nhifConfigRepository;
        this.nssfConfigRepository = nssfConfigRepository;
        this.taxBandRepository = taxBandRepository;
        this.allowanceConfigRepository = allowanceConfigRepository;
    }

    /**
     * Calculates the payroll for a given employee and returns a PayrollSummary
     * record.
     * 
     * @param employeeId The ID of the employee.
     * @return PayrollSummary containing computed payroll details.
     */
    public PayrollSummary calculatePayroll(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        double basicSalary = employee.getBasicSalary();
        double allowances = calculateTotalAllowance(employee);
        double grossSalary = basicSalary + allowances;
        double paye = calculatePAYE(employee);
        double nhifDeduction = calculateNHIF(employee);
        double nssfDeduction = calculateNSSF(employee);
        double totalDeductions = paye + nhifDeduction + nssfDeduction;
        double netSalary = grossSalary - totalDeductions;

        return new PayrollSummary(
                employee.getId(),
                employee.getName(),
                basicSalary,
                grossSalary,
                nhifDeduction,
                nssfDeduction,
                paye,
                allowances,
                netSalary);
    }

    /**
     * Calculates the total allowance for the employee based on configured
     * allowances.
     *
     * @param employee the Employee whose allowance is to be calculated.
     * @return the total allowance.
     */
    public double calculateTotalAllowance(Employee employee) {
        double basicSalary = employee.getBasicSalary();
        double totalAllowance = 0;

        List<AllowanceConfig> allowances = allowanceConfigRepository.findAll();
        for (AllowanceConfig allowance : allowances) {
            if (allowance.getIsPercentage()) {
                totalAllowance += basicSalary * (allowance.getValue() / 100);
            } else {
                totalAllowance += allowance.getValue();
            }
        }
        return totalAllowance;
    }

    /**
     * Calculates the PAYE based on the employee's basic salary and tax bands.
     *
     * @param employee the Employee whose tax is to be calculated.
     * @return calculated PAYE amount.
     */
    public double calculatePAYE(Employee employee) {
        double basicSalary = employee.getBasicSalary();
        List<TaxBand> taxBands = taxBandRepository.findAll();
        double taxDue = 0;

        // Sort tax bands by lower limit in ascending order
        taxBands.sort((a, b) -> Double.compare(a.getLowerLimit(), b.getLowerLimit()));

        double remainingSalary = basicSalary;
        for (TaxBand band : taxBands) {
            if (remainingSalary <= 0)
                break;
            double bandRange = band.getUpperLimit() - band.getLowerLimit();
            double taxableInBand = Math.min(remainingSalary, bandRange);
            taxDue += taxableInBand * (band.getTaxRate() / 100);
            remainingSalary -= taxableInBand;
        }
        return taxDue;
    }

    /**
     * Calculates the NHIF deduction based on the employee's basic salary and the
     * applicable NHIF configuration.
     *
     * @param employee the Employee whose NHIF deduction is to be calculated.
     * @return calculated NHIF deduction.
     */
    public double calculateNHIF(Employee employee) {
        double basicSalary = employee.getBasicSalary();
        BigDecimal basicSalaryBD = BigDecimal.valueOf(basicSalary);

        // Find the NHIF configuration where the salary falls between lowerBound and
        // upperBound.
        Optional<NHIFConfig> configOpt = nhifConfigRepository.findAll().stream()
                .filter(config -> basicSalaryBD.compareTo(config.getLowerBound()) >= 0 &&
                        basicSalaryBD.compareTo(config.getUpperBound()) <= 0)
                .findFirst();

        NHIFConfig config = configOpt.orElseGet(() -> nhifConfigRepository.findAll().stream().findFirst()
                .orElse(new NHIFConfig(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                        BigDecimal.ZERO)));

        double nhifDeduction = basicSalary * (config.getRate().doubleValue() / 100);

        if (nhifDeduction < config.getMinContribution().doubleValue()) {
            nhifDeduction = config.getMinContribution().doubleValue();
        }
        if (config.getMaxContribution().doubleValue() > 0
                && nhifDeduction > config.getMaxContribution().doubleValue()) {
            nhifDeduction = config.getMaxContribution().doubleValue();
        }
        return nhifDeduction;
    }

    /**
     * Calculates the NSSF deduction based on the employee's basic salary and NSSF
     * configuration.
     *
     * @param employee the Employee whose NSSF deduction is to be calculated.
     * @return calculated NSSF deduction.
     */
    public double calculateNSSF(Employee employee) {
        NSSFConfig nssfConfig = nssfConfigRepository.findAll().stream().findFirst()
                .orElse(new NSSFConfig());
        double basicSalary = employee.getBasicSalary();

        double tierILimit = nssfConfig.getTierILimit().doubleValue();
        double tierIRate = nssfConfig.getTierIRate().doubleValue();
        double tierIContribution = Math.min(basicSalary, tierILimit) * (tierIRate / 100);

        double tierIILimit = nssfConfig.getTierIILimit().doubleValue();
        double tierIIRate = nssfConfig.getTierIIRate().doubleValue();
        double tierIIContribution = 0;
        if (basicSalary > tierILimit) {
            double taxableForTierII = Math.min(basicSalary, tierIILimit) - tierILimit;
            tierIIContribution = taxableForTierII * (tierIIRate / 100);
        }
        return tierIContribution + tierIIContribution;
    }
}
