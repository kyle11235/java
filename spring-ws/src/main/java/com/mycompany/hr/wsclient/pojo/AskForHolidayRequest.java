package com.mycompany.hr.wsclient.pojo;

public class AskForHolidayRequest {

	private Holiday holiday;
	private Employee employee;
	
	public AskForHolidayRequest() {
		
	}
	
	public AskForHolidayRequest(Holiday holiday,Employee employee) {
		this.holiday = holiday;
		this.employee = employee;
	}

	public Holiday getHoliday() {
		return holiday;
	}
	public void setHoliday(Holiday holiday) {
		this.holiday = holiday;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	
}
