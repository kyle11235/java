/* Copyright © 2016 Oracle and/or its affiliates. All rights reserved. */

package com.example.rest.dbcs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.util.Config;

public class EmployeeDatabaseDAOImpl extends OracleDS implements EmployeeDAO{
    
    List<Employee> eList = null;
	EmployeeDAO edao = new MockDAOImpl();


	public List<Employee> query(String sqlQueryStr) {
		List<Employee> resultList = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs =null;
		Long start = System.currentTimeMillis();

		try {

			connection = super.getConnection();
			stmt  = connection.prepareStatement(sqlQueryStr);

			rs = stmt.executeQuery();

			Long end = System.currentTimeMillis();
			System.out.println("db -> app:" + (end - start));
			
			while (rs.next()) {
				resultList.add(
                    new Employee(rs.getLong("ID"), rs.getString("FIRSTNAME"), 
                        rs.getString("LASTNAME"), rs.getString("EMAIL"), rs.getString("PHONE"), 
                        rs.getString("BIRTHDATE"), rs.getString("TITLE"), rs.getString("DEPARTMENT"))
                );
			}
		} catch (SQLException e) {
			System.out.println("SQL Query Error: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
            System.out.println("Query Error: " + e.getMessage());
            e.printStackTrace();
		} finally{
			try {
				if(rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("cannot close resultset/statement/connection", e);
			}
		}
		return resultList;
	}

    @Override
    public List<Employee> getAllEmployees(){

		Boolean mockMode = Boolean.parseBoolean(Config.getValue("app.mockMode"));
		if(mockMode){
			return edao.getAllEmployees();
		}


		String queryStr = "SELECT * FROM EMPLOYEE";
		List<Employee> resultList = this.query(queryStr);
        return resultList;	
    }
   

    @Override
    public Employee getEmployee(long id){
		String queryStr = "SELECT * FROM EMPLOYEE WHERE ID=" + id;
		List<Employee> resultList = this.query(queryStr);
                
		if (resultList.size() > 0) {
			return resultList.get(0);
		} else {
            return null;
        }    
    }
    

    @Override
    public List<Employee> getByLastName(String name){
		String queryStr = "SELECT * FROM EMPLOYEE WHERE LASTNAME LIKE '" + name + "%'";
		List<Employee> resultList = this.query(queryStr);
         
        return resultList;
    }
    
    
    @Override
    public List<Employee> getByTitle(String title){
		String queryStr = "SELECT * FROM EMPLOYEE WHERE TITLE LIKE '" + title + "%'";
		List<Employee> resultList = this.query(queryStr);
         
        return resultList;
    }

    
    @Override
    public List<Employee> getByDepartment(String department){
		String queryStr = "SELECT * FROM EMPLOYEE WHERE DEPARTMENT LIKE'" + department + "%'";
		List<Employee> resultList = this.query(queryStr);
         
        return resultList;
    }
    
    
    @Override
    public boolean add(Employee employee){

		Boolean mockMode = Boolean.parseBoolean(Config.getValue("app.mockMode"));
		if(mockMode){
			return edao.add(employee);
		}


		String insertTableSQL = "INSERT INTO EMPLOYEE "
				+ "(ID, FIRSTNAME, LASTNAME, EMAIL, PHONE, BIRTHDATE, TITLE, DEPARTMENT) "
				+ "VALUES(EMPLOYEE_SEQ.NEXTVAL,?,?,?,?,?,?,?)";

		Connection connection = null;
		PreparedStatement stmt = null;
		Long start = System.currentTimeMillis();

		try {

			connection = super.getConnection();
			stmt  = connection.prepareStatement(insertTableSQL);

			stmt.setString(1, employee.getFirstName());
			stmt.setString(2, employee.getLastName());
			stmt.setString(3, employee.getEmail());
			stmt.setString(4, employee.getPhone());
			stmt.setString(5, employee.getBirthDate());
			stmt.setString(6, employee.getTitle());
			stmt.setString(7, employee.getDepartment());

			stmt.execute();

			Long end = System.currentTimeMillis();
			System.out.println("db -> app:" + (end - start));
			
            return true;
		} catch (SQLException e) {
            System.out.println("SQL Add Error: " + e.getMessage());
            e.printStackTrace();
            return false;
            
		} catch (Exception e) {
            System.out.println("Add Error: " + e.getMessage());
            e.printStackTrace();
            return false;
		} finally{
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("cannot close resultset/statement/connection", e);
			}
		}
        
    }
    
    
    @Override
    public boolean update(long id, Employee employee){

		Boolean mockMode = Boolean.parseBoolean(Config.getValue("app.mockMode"));
		if(mockMode){
			return edao.update(id, employee);
		}


		String updateTableSQL = "UPDATE EMPLOYEE SET FIRSTNAME=?, LASTNAME=?, EMAIL=?, PHONE=?, BIRTHDATE=?, TITLE=?, DEPARTMENT=?  WHERE ID=?";

		Connection connection = null;
		PreparedStatement stmt = null;
		Long start = System.currentTimeMillis();


		try {

			connection = super.getConnection();
			stmt  = connection.prepareStatement(updateTableSQL);

			stmt.setString(1, employee.getFirstName());
			stmt.setString(2, employee.getLastName());
			stmt.setString(3, employee.getEmail());
			stmt.setString(4, employee.getPhone());
			stmt.setString(5, employee.getBirthDate());
			stmt.setString(6, employee.getTitle());
			stmt.setString(7, employee.getDepartment());
            stmt.setLong(8, employee.getId());

			stmt.execute();

            return true;
		} catch (SQLException e) {
            System.out.println("SQL Update Error: "	+ e.getMessage());
            e.printStackTrace();
            return false;            
		} catch (Exception e) {
            System.out.println("Update Error: "	+ e.getMessage());
            e.printStackTrace();
            return false;            
		} finally{
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("cannot close resultset/statement/connection", e);
			}
		}
    
    }

    
    @Override
    public boolean delete(long id){
		String deleteRowSQL = "DELETE FROM EMPLOYEE WHERE ID=?";

		Connection connection = null;
		PreparedStatement stmt = null;
		Long start = System.currentTimeMillis();


		try {

			connection = super.getConnection();
			stmt  = connection.prepareStatement(deleteRowSQL);

			stmt.setLong(1, id);
			stmt.execute();
            return true;

		} catch (SQLException e) {
			System.out.println("SQL Delete Error: " + e.getMessage());
            e.printStackTrace();
            return false;
		} catch (Exception e) {
			System.out.println("Delete Error: " + e.getMessage());
            e.printStackTrace();
            return false;
		} finally{
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("cannot close resultset/statement/connection", e);
			}
		}
	}
}
