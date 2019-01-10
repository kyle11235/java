package com.oracle.hcm.controller;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.oracle.hcm.model.Response;
import com.oracle.hcm.service.EmployeeService;


@Path("/employee")
@Singleton
public class EmployeeController {

	private static Logger logger = Logger.getLogger(EmployeeController.class);
	private EmployeeService employeeService = new EmployeeService();
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployees(@QueryParam("q") String q, @QueryParam("ids") String ids) {
		logger.debug("employee controller");
		Response response = new Response();
		response.setEmployees(employeeService.getEmployees(q, ids));
		return response;
	}

}
