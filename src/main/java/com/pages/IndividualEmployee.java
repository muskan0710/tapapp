package com.pages;


import com.dto.EmployeeDto;
import com.model.Employee;
import com.services.serviceImpl.EmployeeImpl;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Import(stylesheet = "context:mybootstrap/css/popup.css",library={"context:jquery/popup.js"})
public class IndividualEmployee {

    @Property
    private long empId;

    @Inject
    private EmployeeImpl employeeImpl;

    @Property
    private Employee employee;

    @Property
    private List<EmployeeDto> employeeList;

    @Property
    private EmployeeDto employeeDto;

    @Inject
    private Request request;

    public void set(Long empId) {
        this.empId = empId;
    }

    void onActivate(Long empId) {
        this.empId = empId;
    }
    void setUpRender() {
        employee = employeeImpl.getById(empId);
        }

    Long onPassivate() {
        return empId;
    }
    void onRefreshZone() {
        employeeList = employeeImpl.getEmployeeBook()
                .stream()
                .map(emp -> new EmployeeDto(emp.getId(), emp.getFirstName(), emp.getLastName(), emp.getDob(),
                        emp.getGender(), emp.getAge(), emp.getAddress(),emp.getDesignation(), emp.getEmail(),
                        emp.getImage()))
                .collect(Collectors.toList());
    }

}