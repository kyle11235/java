/* Copyright © 2016 Oracle and/or its affiliates. All rights reserved. */

package com.example.rest;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;


public class CustomerDatabaseDAOImpl extends OracleDS implements CustomerDAO{
    
	public static final String QUERY = "SELECT C_CUSTKEY,C_NAME,C_ADDRESS,C_CITY,C_NATION,C_REGION,C_PHONE,C_MKTSEGMENT FROM ssb.customer where rownum <= ?";

    @Override
    public List<Customer> get(Integer num){
		List<Customer> resultList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs =null;
		Long start = System.currentTimeMillis();
		try {
			connection = super.getConnection();
			stmt  = connection.prepareStatement(QUERY);
			stmt.setInt(1, num);
			rs = stmt.executeQuery();
			
			Long end = System.currentTimeMillis();
			System.out.println("db -> app:" + (end - start));
			
			while (rs.next()) {
				resultList.add(
                    new Customer(rs.getString("C_CUSTKEY"), rs.getString("C_NAME"), 
                        rs.getString("C_ADDRESS"), rs.getString("C_CITY"), rs.getString("C_NATION"), 
                        rs.getString("C_REGION"), rs.getString("C_PHONE"), rs.getString("C_MKTSEGMENT"))
                );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
   
    

    
}
