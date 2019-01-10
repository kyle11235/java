package com.oracle.hcm.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.oracle.hcm.dao.EmployeeDao;
import com.oracle.hcm.model.Employee;


public class EmployeeService {

	private static Logger logger = Logger.getLogger(EmployeeService.class);
	private EmployeeDao employeeDao = new EmployeeDao();
	private EmployeeSearchService employeeSearchService = new EmployeeSearchService();

	public List<Employee> getEmployees(String q, String ids) {
		List<Integer> idsList = new LinkedList<Integer>();
		Map<Integer, Employee> employeeMap = new HashMap<Integer, Employee>();
		logger.debug("q=" + q);
		logger.debug("ids=" + ids);
		if (q != null) {
			List<Employee> employees = employeeSearchService.getEmployees(q);
			for (Employee employee : employees) {
				idsList.add(employee.getPerson_number());
				employeeMap.put(employee.getPerson_number(), employee);
			}
			List<Employee> employeesFromDB = employeeDao.getEmployees(idsList);
			for (Employee employeeFromDB : employeesFromDB) {
				employeeFromDB.setHighlights(employeeMap.get(employeeFromDB.getPerson_number()).getHighlights());
			}

			return employeesFromDB;
		}
		if (ids != null && !ids.trim().isEmpty()) {
			for (String id : ids.split(",")) {
				idsList.add(Integer.parseInt(id));
			}
			return employeeDao.getEmployeesFulldata(idsList);
		}
		return new LinkedList<Employee>();
	}

}
