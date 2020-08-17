package com.services.serviceImpl;

import com.model.Employee;
import com.services.ValidationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service

public class ValidationImpl implements ValidationService {

    @Autowired
    private EmployeeImpl employeeImpl;

    @Override
    public boolean validate(String email, String password) {
        Employee employee = employeeImpl.getByEmail(email);
        if (employee != null) {
            return Objects.equals(employee.getEmail(), email) && employee.getPassword().equals(password);
        } else {
            return false;
        }

    }
}