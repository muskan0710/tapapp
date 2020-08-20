package com.pages;

import com.model.Employee;
import com.services.EmployeeService;
import com.services.ValidationService;
import com.services.serviceImpl.ValidationImpl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Login {

    @Inject
    private Logger logger;

    @Inject
    private AlertManager alertManager;

    @InjectComponent
    private Form login;

    @InjectComponent("email")
    private TextField emailField;

    @InjectComponent("password")
    private PasswordField passwordField;

    @Property
    private String email;

    @Property
    private String password;

    @Property
    private boolean rememberMe;

    @Property
    private boolean show;

    @InjectPage
    private EmployeeList employeeList;

    @Inject
    private ValidationService validationService;

    @Inject
    private EmployeeService employeeService;

    @InjectPage
    private IndividualEmployee eachEmployee;

    private static final transient Logger log = LoggerFactory.getLogger(Login.class);

    public void main(String[] args) {

        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(email, password);
        if (rememberMe)
            token.setRememberMe(true);
        else
            token.setRememberMe(false);
        try {
            currentUser.login(token);
        } catch (UnknownAccountException uae) {
            log.info("There is no user with username of " + token.getPrincipal());
        } catch (IncorrectCredentialsException ice) {
            log.info("Password for account " + token.getPrincipal() + " was incorrect!");
        } catch (LockedAccountException lae) {
            log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                    "Please contact your administrator to unlock it.");
        }
        // ... catch more exceptions here (maybe custom ones specific to your application?
        catch (AuthenticationException ae) {
            //unexpected condition?  error?
        }

        //all done - log out!
        currentUser.logout();

    }

    void onValidateFromLogin() {
        if (!validationService.validate(email, password)) {
            login.recordError(emailField, "Incorrect Username");
            login.recordError(passwordField, "Incorrect Password");
        }
    }

    Object onSuccessFromLogin() {
        Employee employee = employeeService.getByEmail(email);
        logger.info("Login successful!");
        alertManager.success("Welcome aboard!");
        eachEmployee.set(employee.getId());
        return eachEmployee;
    }

    void onFailureFromLogin() {
        logger.warn("Login error!");
        alertManager.error("Incorrect credentials!");
    }

}

