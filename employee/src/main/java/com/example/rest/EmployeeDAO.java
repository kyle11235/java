/* Copyright © 2016 Oracle and/or its affiliates. All rights reserved. */

package com.example.rest;

import java.util.List;

public interface EmployeeDAO {
    public List<Employee> getAllEmployees();
    public Employee getEmployee(long id);
    public List<Employee> getByLastName(String name);
    public List<Employee> getByTitle(String title);
    public List<Employee> getByDepartment(String department);
    public boolean add(Employee employee);  // False equals fail
    public boolean update(long id, Employee employee); // False equals fail
    public boolean delete(long id); // False equals fail
}
