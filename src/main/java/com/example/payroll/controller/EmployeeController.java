package com.example.payroll.controller;

import com.example.payroll.model.Employee;
import com.example.payroll.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController // Indicates that the class handles http requests
@RequestMapping("/api/employee") // Base url for all endpoints in the controller 
public class EmployeeController {
    private final EmployeeService employeeService;

    // Constructor injection to get EmployeeService instances
    @Autowired
    public EmployeeController (EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    // Get a list of all employees
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    // Get a single employee by id
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id){
        Optional <Employee> employee = employeeService.getEmployeeById(id);

        if(employee.isPresent()){
            return employee.get();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not founf with id: " + id);
        }
    }

    // Post: Create a new employee
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }

    //Put: Update an exisiting employee
    public Employee upadateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
        Employee employee = employeeService.getEmployeeById(id).orElseThrow(() -> new RuntimeException("Employee not found with the id: " + id));

        // Update Feilds
        employee.setName(employeeDetails.getName());
        employee.setBasicSalary(employeeDetails.getBasicSalary());

        return employeeService.saveEmployee(employee);
    }

    // Delete employee by id
    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);

        return "Employee deleted with id: " + id;
    }
}
