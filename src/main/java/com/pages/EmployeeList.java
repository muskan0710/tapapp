package com.pages;

import java.util.List;
import java.util.stream.Collectors;

import com.dto.EmployeeDto;
import com.services.serviceImpl.EmployeeImpl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

@Import(stylesheet = "context:mybootstrap/css/popup.css",library = {"context:jquery/popup.js"})

public class EmployeeList {

    @Property
    private List<EmployeeDto> employees;

    @Property
    private EmployeeDto employee;

    @Property
    private String designation;

    @Inject
    private EmployeeImpl employeeImpl;

    void setupRender() {
        employees = getEmployeeList();
    }

    public List<EmployeeDto> getEmployeeList() {
        return employeeImpl.getEmployeeBook()
                .stream()
                .map(i -> new EmployeeDto(i.getId(), i.getFirstName(), i.getLastName(), i.getDob(), i.getGender(),
                        i.getAge(), i.getAddress(), i.getDesignation(), i.getEmail(), i.getImage()))
                .collect(Collectors.toList());
    }

    void onActivate(){this.designation = designation;}

    String onPassivate(){return designation;}

    void onPromote() {designation="Manager";}
}


