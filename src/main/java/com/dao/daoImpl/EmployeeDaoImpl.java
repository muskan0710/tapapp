package com.dao.daoImpl;

import com.dao.EmployeeDao;
import com.model.Employee;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Employee findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return (Employee) session.createCriteria(Employee.class)
                .add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public Employee findByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        return (Employee) session.createCriteria(Employee.class)
                .add(Restrictions.eq("email", email)).uniqueResult();
    }

    @Override
    public List<Employee> findEmployeeBook() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Employee.class).list();
    }

    @Override
    public void save(Employee employee) {
        Session session = sessionFactory.getCurrentSession();
        session.save(employee);
    }

    private DetachedCriteria getCriteria(String keyword) {
        DetachedCriteria dc = DetachedCriteria.forClass(Employee.class);
        dc.add(Restrictions.sqlRestriction(" exists ( select 1 from employee emp" +
                        " where emp.id  = {alias}.id " +
                        " and emp.id in (select id from search_employee where content @@ " + tsearchQueryString() + " ) "
                , new Object[]{keyword}
                , new Type[]{StandardBasicTypes.STRING}));
        return dc;
    }

    private String tsearchQueryString() {
        return "to_tsquery('simple', regexp_replace(cast(plainto_tsquery('simple', ?) as text), E'\\'(\\\\w+)\\'', '\\1:*', 'g')))";
    }

    @Override
    public List<Employee> searchEmployee(String keyword) {
        return getCriteria(keyword)
                .getExecutableCriteria(sessionFactory.getCurrentSession())
                .list();
    }

}