package com.pages;

import com.dto.EmployeeDto;
import com.model.Employee;
import com.services.EmployeeService;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import java.util.List;
import java.util.stream.Collectors;

@Import(stylesheet = "context:mybootstrap/css/popup.css", library = {"context:jquery/popup.js"})

public class EmployeeList {

    @Property
    private List<EmployeeDto> employees;

    @Property
    private EmployeeDto employee;

    @Property
    private String keyword;

    @InjectComponent
    private Zone empZone;

    @Inject
    private EmployeeService employeeService;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    void setupRender() {
        employees = getEmployeeList(employeeService.getEmployeeBook());
        ajaxResponseRenderer.addRender(empZone);
    }

    private List<EmployeeDto> getEmployeeList(List<Employee> empList) {
        return empList
                .stream()
                .map(i -> new EmployeeDto(i.getId(), i.getFirstName(), i.getLastName(), i.getDob(), i.getGender(),
                        i.getAge(), i.getAddress(), i.getDesignation(), i.getEmail(), i.getImage()))
                .collect(Collectors.toList());
    }

    void onPromote(Long id) {
        employeeService.promoteEmployee(id);
    }

    void onRefreshZone() {
        List<Employee> emp = employeeService.getEmployeeBook();
        ajaxResponseRenderer.addRender(empZone);
    }

    void onSuccessFromSearchEmployee() {
        employees = getEmployeeList(employeeService.searchEmployee(keyword));
        ajaxResponseRenderer.addRender(empZone);
    }

}


