package com.services;

import com.dto.NewEmployeeDto;
import com.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {

    public Employee getById(Long id);

    public Employee getByEmail(String email);

    public List<Employee> getEmployeeBook();

    public void save(NewEmployeeDto newEmployee);

    public void update(Long id,int age);

    public void promoteEmployee(Long id);

    public List<Employee> searchEmployee(String keyword);

}