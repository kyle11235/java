package com.oracle.hcm.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Employee {

	private Integer person_number;
	private String name;
	private Integer age;
	private String hired_year;
	private String department;
	private String job;
	private String grade_name;
	private String grade_step_name;
	private Integer years_worked;
	private String marital_status;
	private String assignment_category;
	private String oversea_experience;
	private String phone_number;
	private String email;
	private String product;
	private String func;
	private String location;

	private List<Assignment> assignments = new LinkedList<Assignment>();
	private List<Profile> profiles = new LinkedList<Profile>();
	private List<Training> trainings = new LinkedList<Training>();
	private List<Evaluation> evaluations = new LinkedList<Evaluation>();
	private List<Qualification> qualifications = new LinkedList<Qualification>();
	private List<Compentency> compentencies = new LinkedList<Compentency>();

	private String emp_career_plan;
	private String manager_comment;
	private String career_preference;
	private String location_preference;

	private Map<String, List<String>> highlights = new HashMap<String, List<String>>();

	public Integer getPerson_number() {
		return person_number;
	}

	public void setPerson_number(Integer person_number) {
		this.person_number = person_number;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGrade_name() {
		return grade_name;
	}

	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}

	public String getGrade_step_name() {
		return grade_step_name;
	}

	public void setGrade_step_name(String grade_step_name) {
		this.grade_step_name = grade_step_name;
	}

	public List<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}

	public List<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(List<Training> trainings) {
		this.trainings = trainings;
	}

	public String getEmp_career_plan() {
		return emp_career_plan;
	}

	public void setEmp_career_plan(String emp_career_plan) {
		this.emp_career_plan = emp_career_plan;
	}

	public String getManager_comment() {
		return manager_comment;
	}

	public void setManager_comment(String manager_comment) {
		this.manager_comment = manager_comment;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	public List<Evaluation> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	public Map<String, List<String>> getHighlights() {
		return highlights;
	}

	public void setHighlights(Map<String, List<String>> highlights) {
		this.highlights = highlights;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getHired_year() {
		return hired_year;
	}

	public void setHired_year(String hired_year) {
		this.hired_year = hired_year;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Integer getYears_worked() {
		return years_worked;
	}

	public void setYears_worked(Integer years_worked) {
		this.years_worked = years_worked;
	}

	public String getMarital_status() {
		return marital_status;
	}

	public void setMarital_status(String marital_status) {
		this.marital_status = marital_status;
	}

	public String getAssignment_category() {
		return assignment_category;
	}

	public void setAssignment_category(String assignment_category) {
		this.assignment_category = assignment_category;
	}

	public String getOversea_experience() {
		return oversea_experience;
	}

	public void setOversea_experience(String oversea_experience) {
		this.oversea_experience = oversea_experience;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Compentency> getCompentencies() {
		return compentencies;
	}

	public void setCompentencies(List<Compentency> compentencies) {
		this.compentencies = compentencies;
	}

	public List<Qualification> getQualifications() {
		return qualifications;
	}

	public void setQualifications(List<Qualification> qualifications) {
		this.qualifications = qualifications;
	}

	public String getCareer_preference() {
		return career_preference;
	}

	public void setCareer_preference(String career_preference) {
		this.career_preference = career_preference;
	}

	public String getLocation_preference() {
		return location_preference;
	}

	public void setLocation_preference(String location_preference) {
		this.location_preference = location_preference;
	}

}
