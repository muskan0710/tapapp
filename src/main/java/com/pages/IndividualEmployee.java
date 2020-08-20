package com.pages;

import com.dto.EmployeeDto;
import com.model.Employee;
import com.services.EmployeeService;
import com.services.serviceImpl.EmployeeImpl;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import java.util.List;
import java.util.stream.Collectors;

public class IndividualEmployee {

    @Property
    private long empId;

    @Inject
    private EmployeeService employeeService;

    @Property
    private Employee employee;

    @Property
    private List<EmployeeDto> employeeList;

    @Property
    private EmployeeDto employeeDto;

    @Inject
    private Request request;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    public void set(Long empId) {
        this.empId = empId;
    }

    void onActivate(Long empId) {
        this.empId = empId;
    }

    void setUpRender() {
        employee = employeeService.getById(empId);
    }

    Long onPassivate() {
        return empId;
    }
}