package com.pages;

import com.model.Employee;
import com.services.serviceImpl.EmployeeImpl;
import com.services.serviceImpl.ValidationImpl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
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
    private ValidationImpl validateImpl;

    @Inject
    private EmployeeImpl employeeImpl;

    @InjectPage
    private IndividualEmployee eachEmployee;
        private static final transient Logger log = LoggerFactory.getLogger(Login.class);

        public void main(String[] args) {

            Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
            SecurityManager securityManager = factory.getInstance();
            SecurityUtils.setSecurityManager(securityManager);
            // get the currently executing user:
            Subject currentUser = SecurityUtils.getSubject();

            // Do some stuff with a Session (no need for a web or EJB container!!!)
         //   Session session = currentUser.getSession();
          //  session.setAttribute("someKey", "aValue");
            //String value = (String) session.getAttribute("someKey");
            //if (value.equals("aValue")) {
              //  log.info("Retrieved the correct value! [" + value + "]");
           // }

            // let's login the current user so we can check against roles and permissions:
            if (!currentUser.isAuthenticated()) {
                UsernamePasswordToken token = new UsernamePasswordToken(email, password);
                if(rememberMe)
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
            }

            //say who they are:
            //print their identifying principal (in this case, a username):
            log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");

            //test a role:
            if (currentUser.hasRole("admin")) {
                show=true;
            } else {
                show=false;
            }

            //test a typed permission (not instance-level)
            if (currentUser.isPermitted("employeeViewPermission")) {
                log.info("You may use a lightsaber ring.  Use it wisely.");
            } else {
                log.info("Sorry, lightsaber rings are for schwartz masters only.");
            }

            //a (very powerful) Instance Level permission:
            if (currentUser.isPermitted("employeeViewPermission")) {
                log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
                        "Here are the keys - have fun!");
            } else {
                log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
            }

            //all done - log out!
            currentUser.logout();

        }
    void onValidateFromLogin() {
        if (!validateImpl.validate(email, password)) {
            login.recordError(emailField, "Incorrect Username");
            login.recordError(passwordField, "Incorrect Password");
        }
    }


    Object onSuccessFromLogin() {
        Employee employee = employeeImpl.getByEmail(email);
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

