package com.services.serviceImpl;

import com.dao.EmployeeDao;
import com.dto.NewEmployeeDto;
import com.model.Employee;
import com.services.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    public Employee getById(Long id) {
        return employeeDao.findById(id);
    }

    public Employee getByEmail(String email) {
        return employeeDao.findByEmail(email);
    }

    public List<Employee> getEmployeeBook() {
        return employeeDao.findEmployeeBook();
    }

    public void save(NewEmployeeDto newEmployee) {
        Employee employee = new Employee(
                newEmployee.getFirstName(),
                newEmployee.getLastName(),
                newEmployee.getDob(),
                newEmployee.getGender(),
                newEmployee.getAge(),
                newEmployee.getAddress(),
                newEmployee.getDesignation(),
                newEmployee.getEmail(),
                newEmployee.getPassword(),
                newEmployee.getImage());
        employeeDao.save(employee);
    }

    public void update(Long id,int age) {
        Employee employee = employeeDao.findById(id);
        employee.setAge(age);
    }

    @Transactional
    public void promoteEmployee(Long id) {
        Employee employee = employeeDao.findById(id);
        if(employee!=null){
            employee.setDesignation("Manager");
            employeeDao.save(employee);
        }
    }

    @Transactional
    @Override
    public List<Employee> searchEmployee(String keyword) {
        return employeeDao.searchEmployee(keyword);
    }

}