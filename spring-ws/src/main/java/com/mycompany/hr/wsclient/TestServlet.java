package com.mycompany.hr.wsclient;


import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mycompany.hr.wsclient.pojo.AskForHolidayRequest;
import com.mycompany.hr.wsclient.pojo.Employee;
import com.mycompany.hr.wsclient.pojo.Holiday;


public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//autowire does not work in servlet while new WebSeriveClient... needs many configuration
		
		Holiday holiday = new Holiday(new Date(),new Date());
		Employee employee =  new Employee(100, "peng", "zhang");
		AskForHolidayRequest request = new AskForHolidayRequest(holiday, employee);
		
		
	}

	
	
	
	
}
