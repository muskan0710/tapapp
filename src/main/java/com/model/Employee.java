package com.model;


import org.apache.tapestry5.beaneditor.Validate;

import javax.persistence.*;

import java.util.Date;

import static java.lang.String.format;

@Entity
@Table(name = "employee")
@SequenceGenerator(name = "employee_sequence", sequenceName = "employee_sequence", allocationSize = 1)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "gender")
    @Validate("required")
    private String gender;

    @Column(name = "age")
    private int age;

    @Column(name = "address")
    private String address;

    @Column(name = "designation")
    private String designation;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "image")
    private String image;

    public Employee() {
    }

    public Employee(String firstName, String lastName, Date dob, String gender, int age, String address, String designation,
                    String email, String password, String image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.designation = designation;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return format("Employee{id=%d, firstName='%s', lastName='%s', dob='%s', gender= %s, age=%d, address='%s', designation='%s', email='%s', password='%s', image='%s}",
                id,
                firstName,
                lastName,
                dob,
                gender,
                age,
                address,
                designation,
                email,
                password,
                image);
    }

}
