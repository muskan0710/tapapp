package com.pages;

import java.util.List;
import java.util.stream.Collectors;

import com.dto.EmployeeDto;
import com.services.EmployeeService;
import com.services.serviceImpl.EmployeeImpl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

@Import(stylesheet = "context:mybootstrap/css/popup.css",library = {"context:jquery/popup.js"})

public class EmployeeList {

    @Property
    private List<EmployeeDto> employees;

    @Property
    private EmployeeDto employee;

    @Inject
    private EmployeeService employeeService;

    void setupRender() {
        employees = getEmployeeList();
    }

    public List<EmployeeDto> getEmployeeList() {
        return employeeService.getEmployeeBook()
                .stream()
                .map(i -> new EmployeeDto(i.getId(), i.getFirstName(), i.getLastName(), i.getDob(), i.getGender(),
                        i.getAge(), i.getAddress(), i.getDesignation(), i.getEmail(), i.getImage()))
                .collect(Collectors.toList());
    }

    void onPromote(Long id) {
        employeeService.promoteEmployee(id);
    }
}


