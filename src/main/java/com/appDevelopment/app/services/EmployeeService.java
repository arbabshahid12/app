package com.appDevelopment.app.services;


import com.appDevelopment.app.entities.Employee;
import com.appDevelopment.app.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employee_repository;
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employee_repository = employeeRepository;
    }

    public List<Employee> getAllEmployee() {
//        return EmployeeRepository.findAll;

        return List.of();
    }
}
