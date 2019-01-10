/* Copyright © 2016 Oracle and/or its affiliates. All rights reserved. */

package com.example.rest;

public class Employee {
  private final long id;
  private final String firstName;
  private final String lastName;
  private final String email;
  private final String phone;
  private final String birthDate;
  private final String title;
  private final String department;
  
  
  public Employee(){
      super();
      id = 0;
      firstName = "";
      lastName = "";
      email = "";
      phone = "";
      birthDate = "";
      title = "";
      department = "";  
  }
   
  public Employee(long id, String firstName, String lastName, String email, String phone, String birthDate, String title, String department){
      this.id = id;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.phone = phone;
      this.birthDate = birthDate;
      this.title = title;
      this.department = department;
  }
  
  public long getId(){
    return this.id;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }
  
  public String getEmail(){
    return this.email;
  }
  
  public String getPhone(){
    return this.phone;
  }

  public String getBirthDate() {
    return this.birthDate;
  }

  public String getTitle() {
    return this.title;
  } 
  
  public String getDepartment(){
    return this.department;
  }
  
  @Override
  public String toString(){
    return "ID: " + id 
        + " First Name: " + firstName
        + " Last Name: " + lastName
        + " EMail: " + email
        + " Phone: " + phone
        + " Birth Date: " + birthDate
        + " Title: " + title
        + " Department: " + department;
  }  
  
}
