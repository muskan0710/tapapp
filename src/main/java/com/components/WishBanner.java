package com.components;

import com.model.Employee;
import com.services.EmployeeService;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@SupportsInformalParameters
public class WishBanner {

    /* Parameter */
    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.PROP)
    private Employee employee;

    private EmployeeService employeeService;

    @BeginRender
    private boolean beginRender(MarkupWriter writer) {
        String birthDate = new SimpleDateFormat("MM/dd").format(employee.getDob());
        String currentDate = new SimpleDateFormat("MM/dd").format(new Date());
        String employeeGender = employee.getGender();
        if (currentDate.equals(birthDate)) {
            if (employeeGender.equals("Female")) {
                writer.element("div", "class", "female");
            } else if (employeeGender.equals("Male")) {
                writer.element("div", "class", "male");
            } else {
                writer.element("div", "class", "other");
            }
            writer.write("Happy Birthday");
            writer.end();
        }
        return false;
    }
}


