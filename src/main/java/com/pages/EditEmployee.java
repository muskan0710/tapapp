package com.pages;

import com.services.EmployeeService;
import com.services.ValidationService;
import com.services.serviceImpl.EmployeeImpl;
import com.services.serviceImpl.ValidationImpl;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

@Import(library = {"context:jquery/age.js"})
public class EditEmployee {
    @Inject
    private Logger logger;

    @Inject
    private AlertManager alertManager;

    @InjectComponent
    private Form edit;

    @InjectComponent("age")
    private TextField ageField;

    @InjectPage
    private IndividualEmployee employee;

    @Property
    private Integer age;

    @Property
    private long empId;

    @Inject
    private ValidationService validation;

    @Inject
    private EmployeeService employeeService;

    public void set(Long empId) {
        this.empId = empId;
    }

    void onActivate(Long empId) {
        this.empId = empId;
    }

    Long onPassivate() {
        return empId;
    }

    void onValidateFromEdit() {
        if(age==null || age>65 && age<18) {
            edit.recordError(ageField, "Invalid!");
        }
    }

    Object onSuccessFromEdit() {
        employeeService.update(empId,age);
        logger.info("Updation successful!");
        employee.set(empId);
        return employee;
    }

    void onFailureFromEdit() {
        logger.warn("Update error!");
        alertManager.error("Updation failed!");
    }

}