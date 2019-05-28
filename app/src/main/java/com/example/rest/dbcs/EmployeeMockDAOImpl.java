/* Copyright © 2016 Oracle and/or its affiliates. All rights reserved. */

package com.example.rest.dbcs;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class EmployeeMockDAOImpl implements EmployeeDAO{
    
    private final CopyOnWriteArrayList<Employee> eList = MockEmployeeList.getInstance();
    
    @Override
    public List<Employee> getAllEmployees(){
        return eList;
    }
   

    @Override
    public Employee getEmployee(long id){
        Employee match = null;
                
        match = eList.stream()
                    .filter(e -> e.getId() == id)
                    .findFirst().orElse(match);
        
        return match;        
    }
    

    @Override
    public List<Employee> getByLastName(String name){
        
       List<Employee> matchList = 
            eList.stream()
                .filter((e) -> (e.getLastName().contains(name)))
                .collect(Collectors.toList());
         
        return matchList;
    }
    
    
    @Override
    public List<Employee> getByTitle(String title){
        List<Employee> matchList = 
            eList.stream()
                .filter((e) -> (e.getTitle().contains(title)))
                .collect(Collectors.toList());
        
        return matchList;
    }

    
    @Override
    public List<Employee> getByDepartment(String department){
        List<Employee> matchList = 
            eList.stream()
                .filter((e) -> (e.getDepartment().contains(department)))
                .collect(Collectors.toList());
        
        return matchList;
    }
    
    
    @Override
    public boolean add(Employee employee){
        long next = eList.size() + 100;

        Employee nextEmployee = 
            new Employee( next, employee.getFirstName(), employee.getLastName(),
                employee.getEmail(), employee.getPhone(), 
                employee.getBirthDate(), employee.getTitle(), employee.getDepartment());
            
        eList.add(nextEmployee);
        return true;
    }
    
    
    @Override
    public boolean update(long id, Employee employee){
        int matchIndex = -1;
        
        matchIndex = eList.stream()
                    .filter(e -> e.getId() == id)
                    .findFirst()
                    .map(e -> eList.indexOf(e))
                    .orElse(matchIndex);
               
        if (matchIndex > -1){
            eList.set(matchIndex, employee);
            return true;           
        } else {
            return false;           
        }                
    
    }

    
    @Override
    public boolean delete(long id){
        int matchIndex = -1;
        
        matchIndex = eList.stream()
                    .filter(e -> e.getId() == id)
                    .findFirst()
                    .map(e -> eList.indexOf(e))
                    .orElse(matchIndex);
                
        if (matchIndex > -1){
            eList.remove(matchIndex);
            return true;           
        } else {
            return false;           
        }                
    }

}
