package com.example.payroll.service;

import com.example.payroll.model.Employee;
import com.example.payroll.repositories.EmployeeRepository;
import com.example.payroll.model.NHIFConfig;
import com.example.payroll.model.NSSFConfig;
import com.example.payroll.model.TaxBand;
import com.example.payroll.repositories.NHIFConfigRepository;
import com.example.payroll.repositories.NSSFConfigRepository;
import com.example.payroll.repositories.TaxBandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    // Injecting the Repository to interact with the Database
    private final EmployeeRepository employeeRepository;
    private final NHIFConfigRepository nhifConfigRepository;
    private final NSSFConfigRepository nssfConfigRepository;
    private final TaxBandRepository taxBandRepository;
    


    @Autowired // Construtor injection for the repository
    public EmployeeService(EmployeeRepository employeeRepository
            , NHIFConfigRepository nhifConfigRepository
            , NSSFConfigRepository nssfConfigRepository
            , TaxBandRepository taxBandRepository){ 
        this.employeeRepository = employeeRepository;
        this.nhifConfigRepository = nhifConfigRepository;
        this.nssfConfigRepository = nssfConfigRepository;
        this.taxBandRepository = taxBandRepository;
    }

    // Method to retrieve the Employees from the database
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    // Method to retrive a single employee by the id
    public  Optional<Employee> getEmployeeById(Long Id){
        return employeeRepository.findById(Id);
    }

    // Method to create or add an Employee
    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    // Method to delete an employee by id
    public void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
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
}
