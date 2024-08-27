package com.appDevelopment.app.controller;

import com.appDevelopment.app.entities.Employee;
import com.appDevelopment.app.repositories.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/findAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/getEmployeeByEmail/{email}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("email") String email) {
        Employee employee = employeeRepository.findByEmail(email);
        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<String> addEmployee(@Valid @RequestBody Employee employee) {
        if (isValidPassword(employee.getPassword())) {
            return new ResponseEntity<>("Password must be at least 8 characters long and contain uppercase, lowercase, and digits.", HttpStatus.BAD_REQUEST);
        }
        Employee emp = new Employee();
        emp.setPassword(employee.getPassword());
        employeeRepository.save(emp);
            return new ResponseEntity<>("Employee added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/updateEmployee/{email}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("email") String email, @Valid @RequestBody Employee employee) {
        if (isValidPassword(employee.getPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Employee emp = employeeRepository.findByEmail(email);
        if (emp !=null) {
            emp.setPassword(employee.getPassword());
            employeeRepository.save(emp);
            return new ResponseEntity<>(emp, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteEmployee/{email}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("email") String email) {
        Employee res = employeeRepository.findByEmail(email);
        if (res !=null) {
            employeeRepository.delete(res);
            return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to delete employee", HttpStatus.BAD_REQUEST);
    }

    @QueryMapping(name = "findAllEmployees")
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    @QueryMapping(name = "getEmployee")
    public Employee findEmployeeByEmail(@Argument String email) {
        return employeeRepository.findByEmail(email);
    }

    @MutationMapping(name = "deleteEmployee")
    public Boolean deleteEmployeeByEmail(@Argument String email) {
        Employee employee = employeeRepository.findByEmail(email);
        if (employee != null) {
            employeeRepository.delete(employee);
            return true;
        }
        return false;
    }

    @PatchMapping("/{email}/password")
    public ResponseEntity<String> updateEmployeePasswordBody(@PathVariable("email") String email, @Valid @RequestBody String newPasswordBody) {
        try {
            Employee employee = employeeRepository.findByEmail(email);
            if (employee != null) {
                employee.setPassword(newPasswordBody);
                employeeRepository.save(employee);
                return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Failed to update password", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
        return !password.matches(regex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            errors.append(errorMessage).append("; ");
        });
        return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
    }
}
