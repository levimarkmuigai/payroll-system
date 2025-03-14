package com.example.payroll.service;

import com.example.payroll.model.Employee;
import com.example.payroll.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    // Injecting the Repository to interact with the Database
    private final EmployeeRepository employeeRepository;

    @Autowired // Construtor injection for the repository
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
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

   
}
