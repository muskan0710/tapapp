package com.dao;

import com.model.Employee;

import java.util.List;

public interface EmployeeDao {
    Employee findById(Long id);

    Employee findByEmail(String email);

    List<Employee> findEmployeeBook();

    void save(Employee employee);
}
