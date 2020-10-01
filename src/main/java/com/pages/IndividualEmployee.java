package com.pages;

import com.components.WishBanner;
import com.dto.EmployeeDto;
import com.model.Employee;
import com.services.EmployeeService;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import java.util.List;

@Import(stylesheet = "context:mybootstrap/css/birthday.css")
public class IndividualEmployee {

    @Property
    private long empId;

    @Component
    private WishBanner banner;

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