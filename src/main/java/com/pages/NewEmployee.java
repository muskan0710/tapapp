package com.pages;

import com.dto.NewEmployeeDto;
import com.services.EmployeeService;
import com.services.serviceImpl.EmployeeImpl;

import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;


public class NewEmployee {
    @Inject
    private Logger logger;

    @Inject
    private AlertManager alertManager;

    @Inject
    private EmployeeService employeeService;

    @Property
    NewEmployeeDto newEmployee;

    @Property
    private int age;

        Object onSuccess() {
        employeeService.save(newEmployee);
        logger.info("Registration successful.");
        alertManager.success("Thanks for registering, Welcome!");
        return Login.class;
    }

    Object onFailure() {
        employeeService.save(newEmployee);
        logger.info("Registration unsuccessful.");
        alertManager.success("Couldn't register!");
        return Error404.class;
    }
}