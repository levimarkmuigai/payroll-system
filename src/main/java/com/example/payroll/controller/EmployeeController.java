package com.example.payroll.controller;

import com.example.payroll.model.Employee;
import com.example.payroll.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing Employee entities.
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    /**
     * Retrieves all employees.
     *
     * @return a list of all Employee objects.
     */
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Retrieves an employee by their ID.
     *
     * @param id the ID of the employee.
     * @return the Employee object wrapped in a ResponseEntity.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new employee.
     *
     * @param employee the Employee object to create.
     * @return the created Employee object.
     */
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * Updates an existing employee.
     *
     * @param id the ID of the employee to update.
     * @param updatedEmployee the updated employee details.
     * @return the updated Employee object wrapped in a ResponseEntity.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(updatedEmployee.getName());
                    employee.setPhoneNumber(updatedEmployee.getPhoneNumber());
                    employee.setEmail(updatedEmployee.getEmail());
                    employee.setGender(updatedEmployee.getGender());
                    employee.setDepartment(updatedEmployee.getDepartment());
                    employee.setBasicSalary(updatedEmployee.getBasicSalary());
                    Employee savedEmployee = employeeRepository.save(employee);
                    return ResponseEntity.ok(savedEmployee);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deletes an employee.
     *
     * @param id the ID of the employee to delete.
     * @return a ResponseEntity with HTTP status 204 if deletion is successful.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        if(employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
