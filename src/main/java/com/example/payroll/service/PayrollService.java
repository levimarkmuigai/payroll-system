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

import java.util.List;

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
     * This method calculates the payroll for a given employee and returns a PayrollSummary DTO.
     * 
     * @param employeeId The ID of the employee for whom the payroll is to be calculated.
     * @return PayrollSummary DTO containing the payroll details.   
    */
    public PayrollSummary calculatePayroll(Long employeeId){
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        double basicSalary = employee.getBasicSalary();
        double allowances = CalculateTotalAllowance(employee);
        double grossSalary = basicSalary + allowances;
        double paye = calaculatePAYE(employee);
        double nhif = calculateNHIF(employee);
        double nssf = calculateNSSF(employee);  
        double totalDeductions = paye + nhif + nssf;
        double netSalary = grossSalary - totalDeductions;
        
        return new PayrollSummary(
            employee.getId(),      // employeeId
            employee.getName(),    // employeeName
            basicSalary,           // basicSalary
            grossSalary,           // grossSalary
            nhif,                  // nhifDeduction
            nssf,                  // nssfDeduction
            paye,
            netSalary,                  // paye
            allowances         // allowanceConfig (the total allowances calculated)
        );
    }

    /**
     * Calaculate the PAYE (income tax) based on the employee's basic salary and tax bands
     * 
     * @param employee the Employee whose tax is to be calculated
     * @return the calculated PAYE amount
    */
    public  double calaculatePAYE(Employee employee){
        double basicSalary = employee.getBasicSalary();
        List<TaxBand> taxBands = taxBandRepository.findAll();
        double taxDue = 0;

        // Sort the tax bands by lower limit
        taxBands.sort((a, b) -> Double.compare(a.getlowerLimit(), b.getlowerLimit()));

        double remainingSalary = basicSalary;
        for(TaxBand band : taxBands){
            if(remainingSalary <= 0 )break;
            double taxableInBand = Math.min(remainingSalary, band.getupperLimit() - band.getlowerLimit());
            taxDue += taxableInBand * (band.gettaxRate() / 100);
            remainingSalary -= taxableInBand;
        }
        return taxDue;
    }

    /**
     * 
     * Calculate the NHIF deduction based on the employee's basic salary and NHIF bands
     * 
     * @param employee the Employee whose NHIF deduction is to be calculated
     * @return the calculated NHIF deduction amount
    */
    public double calculateNHIF(Employee employee){
        NHIFConfig nhifconfig = nhifConfigRepository.findAll().stream().findFirst().orElse(new NHIFConfig());
        double basicSalary = employee.getBasicSalary();
        double nhifDeduction =  basicSalary * (nhifconfig.getRate().doubleValue() / 100);

        // Ensure deduction is not less than minimum
        if(nhifDeduction > nhifconfig.getMinContribution().doubleValue()){
            nhifDeduction = nhifconfig.getMinContribution().doubleValue();
        }

        if(nhifDeduction > 0 && nhifDeduction >nhifconfig.getMaxContribution().doubleValue()){
            nhifDeduction = nhifconfig.getMaxContribution().doubleValue();
        }

        return nhifDeduction;
    }

    /**
     * Calculate the NSSF deduction based on the employee's basic salary and NSSF bands
     * 
     * @param employee the Employee whose NSSF deduction is to be calculated
     * @return the calculated NSSF deduction amount
    */
    public double calculateNSSF(Employee employee){
        NSSFConfig nssfConfig = nssfConfigRepository.findAll().stream().findFirst().orElse(new NSSFConfig());
        double basicSalary = employee.getBasicSalary();
        
        // Calculation Logic using the NSSF tier
        double tier1Contribution = Math.min(basicSalary, nssfConfig.getTierILimit().doubleValue());
        double tier2Base = Math.min(Math.max(basicSalary  - nssfConfig.getTierIILimit().doubleValue(), 0), nssfConfig.getTierIILimit().doubleValue());

        double tier2Contribution = tier2Base * (nssfConfig.getTierIIRate().doubleValue() / 100);
        return tier1Contribution + tier2Contribution; 
    }

    /**
     * Calculate the total allowance of the employee
     * 
     * @param employee the Employee whose total allowance is to be calculated
     * @return the calculated total allowance amount
    */
    public double CalculateTotalAllowance(Employee employee){
        double basicSalary = employee.getBasicSalary();
        double totalAllowance = 0;

       List<AllowanceConfig> allowanceConfig = allownanceConfigRepository.findAll();
       for(AllowanceConfig allowance : allowanceConfig){ 
            if(allowance.getIsPercentage()){
                totalAllowance += basicSalary * (allowance.getValue() / 100);
            }else{
                totalAllowance += allowance.getValue();
            }
        }
        return totalAllowance;
    }
}
