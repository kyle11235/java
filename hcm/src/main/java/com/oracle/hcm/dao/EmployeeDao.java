package com.oracle.hcm.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.oracle.hcm.model.Assignment;
import com.oracle.hcm.model.Compentency;
import com.oracle.hcm.model.Employee;
import com.oracle.hcm.model.Evaluation;
import com.oracle.hcm.model.Profile;
import com.oracle.hcm.model.Qualification;
import com.oracle.hcm.model.Training;

public class EmployeeDao extends BaseDao {

	private static Logger logger = Logger.getLogger(EmployeeDao.class);
	private static String SELECT_INFO = "SELECT person_number, name, age, to_char(start_date, 'yyyy') as HIRED_YEAR, (to_number(to_char(sysdate, 'yyyy')) - to_number(to_char(start_date, 'yyyy')))  as YEARS_WORKED, OVERSEA_EXPERIENCE, MARITAL_STATUS, ASSIGNMENT_CATEGORY, PHONE_NUMBER, EMAIL, department, job, product, funtion, location, grade_name, grade_step_name FROM emp_personal_info WHERE person_number in (IDS)";
	private static String SELECT_ASSIGNMENT = "select start_date, department, job, grade_name from emp_assign_history where person_number = ? ";
	private static String SELECT_TRAINING = "select start_date, end_date, training_course from EMP_TRAINING_HISTORY where person_number = ? ";
	private static String SELECT_CAREER = "select EMP_CAREER_PLAN, MANAGER_COMMENT, career_preference, location_preference from EMP_CARRER_PREFER where person_number =  ? ";
	private static String SELECT_PROFILE = "SELECT PRODUCT_START_DATE, PRODUCT_END_DATE, PRODUCT, ROLE, FUNC_START_DATE, FUNC_END_DATE, FUNCTION, LOC_START_DATE, LOC_END_DATE, CITY FROM EMP_PROFILE WHERE person_number = ? ";
	private static String SELECT_EVALUATION = "SELECT performance_document, rating FROM EMP_EVAL_HISTORY WHERE person_number = ? ";
	private static String SELECT_QUALIFICATION = "select qualification FROM EMP_QUALIFICATIONS WHERE person_number = ? ";
	private static String SELECT_COMPENTENCY = "select COMPETENCY, \"LEVEL\" FROM EMP_COMPETENCY WHERE person_number = ? ";
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public List<Employee> getEmployees(List<Integer> ids) {
		List<Employee> out = new LinkedList<Employee>();
		if (ids == null || ids.isEmpty()) {
			return out;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ids.size(); i++) {
			Integer id = ids.get(i);
			sb.append("'");
			sb.append(id);
			sb.append("'");
			if (i < (ids.size() - 1)) {
				sb.append(",");
			}
		}
		String sql = SELECT_INFO.replaceAll("IDS", sb.toString());
		logger.debug(sql);

		logger.debug("get Employees");
		Connection connection = null;
		try {
			connection = super.getOaaConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Employee employee = new Employee();
				employee.setPerson_number(rs.getInt(1));
				employee.setName(rs.getString(2));
				employee.setAge(rs.getInt(3));
				employee.setHired_year(rs.getString(4));
				employee.setYears_worked(rs.getInt(5));
				employee.setOversea_experience(rs.getString(6));
				employee.setMarital_status(rs.getString(7));
				employee.setAssignment_category(rs.getString(8));
				employee.setPhone_number(rs.getString(9));
				employee.setEmail(rs.getString(10));
				employee.setDepartment(rs.getString(11));
				employee.setJob(rs.getString(12));
				employee.setProduct(rs.getString(13));
				employee.setFunc(rs.getString(14));
				employee.setLocation(rs.getString(15));
				employee.setGrade_name(rs.getString(16));
				employee.setGrade_step_name(rs.getString(17));
				out.add(employee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.debug("get Employees done");
		return out;
	}

	public List<Employee> getEmployeesFulldata(List<Integer> ids) {
		List<Employee> employees = this.getEmployees(ids);
		if (employees != null) {
			for (Employee employee : employees) {
				Integer id = employee.getPerson_number();
				employee.setAssignments(this.getAssignments(id));
				employee.setTrainings(this.getTrainings(id));
				this.setCareerPlan(employee);
				employee.setProfiles(this.getProfiles(id));
				employee.setEvaluations(this.getEvaluations(id));
				employee.setQualifications(this.getQualifications(id));
				employee.setCompentencies(this.getCompentencies(id));
			}
		}
		return employees;
	}

	public List<Assignment> getAssignments(Integer id) {
		List<Assignment> out = new LinkedList<Assignment>();
		if (id == null) {
			return out;
		}

		logger.debug("get getAssignments");
		Connection connection = null;
		try {
			connection = super.getOaaConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_ASSIGNMENT);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Assignment assignment = new Assignment();
				assignment.setStart_date(this.format(rs.getDate(1)));
				assignment.setDepartment(rs.getString(2));
				assignment.setJob(rs.getString(3));
				assignment.setGrade_name(rs.getString(4));
				out.add(assignment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.debug("get Assignments done");
		return out;
	}

	public List<Training> getTrainings(Integer id) {
		List<Training> out = new LinkedList<Training>();
		if (id == null) {
			return out;
		}

		logger.debug("get Trainings");
		Connection connection = null;
		try {
			connection = super.getOaaConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_TRAINING);
			statement.setInt(1, id);
			statement.execute();
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Training training = new Training();
				training.setStart_date(this.format(rs.getDate(1)));
				training.setEnd_date(this.format(rs.getDate(2)));
				training.setTraining_course(rs.getString(3));
				out.add(training);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.debug("get trainings done");
		return out;
	}

	public void setCareerPlan(Employee employee) {
		if (employee == null || employee.getPerson_number() == null) {
			return;
		}

		logger.debug("get setCareerPlan");
		Connection connection = null;
		try {
			connection = super.getOaaConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_CAREER);
			statement.setInt(1, employee.getPerson_number());
			statement.execute();
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				employee.setEmp_career_plan(rs.getString(1));
				employee.setManager_comment(rs.getString(2));
				employee.setCareer_preference(rs.getString(3));
				employee.setLocation_preference(rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.debug("setCareerPlan done");
	}

	public List<Profile> getProfiles(Integer id) {
		List<Profile> out = new LinkedList<Profile>();
		if (id == null) {
			return out;
		}

		logger.debug("get Profiles");
		Connection connection = null;
		try {
			connection = super.getOaaConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_PROFILE);
			statement.setInt(1, id);
			statement.execute();
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Profile profile = new Profile();
				profile.setProduct_start_date(this.format(rs.getDate(1)));
				profile.setProduct_end_date(this.format(rs.getDate(2)));
				profile.setProduct(rs.getString(3));
				profile.setRole(rs.getString(4));
				profile.setFunc_start_date(this.format(rs.getDate(5)));
				profile.setFunc_end_date(this.format(rs.getDate(6)));
				profile.setFunction(rs.getString(7));
				profile.setLoc_start_date(this.format(rs.getDate(8)));
				profile.setLoc_end_date(this.format(rs.getDate(9)));
				profile.setCity(rs.getString(10));
				out.add(profile);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.debug("get Profiles done");
		return out;
	}

	public List<Evaluation> getEvaluations(Integer id) {
		List<Evaluation> out = new LinkedList<Evaluation>();
		if (id == null) {
			return out;
		}

		logger.debug("get Evaluations");
		Connection connection = null;
		try {
			connection = super.getOaaConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_EVALUATION);
			statement.setInt(1, id);
			statement.execute();
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Evaluation evaluation = new Evaluation();
				evaluation.setPerformance_document(rs.getString(1));
				evaluation.setRating(rs.getString(2));
				out.add(evaluation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.debug("get evaluations done");
		return out;
	}

	public List<Qualification> getQualifications(Integer id) {
		List<Qualification> out = new LinkedList<Qualification>();
		if (id == null) {
			return out;
		}

		logger.debug("get qualifications");
		Connection connection = null;
		try {
			connection = super.getOaaConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_QUALIFICATION);
			statement.setInt(1, id);
			statement.execute();
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Qualification qualification = new Qualification();
				qualification.setQualification(rs.getString(1));
				out.add(qualification);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.debug("get qualifications done");
		return out;
	}

	public List<Compentency> getCompentencies(Integer id) {
		List<Compentency> out = new LinkedList<Compentency>();
		if (id == null) {
			return out;
		}

		logger.debug("get compentencies");
		Connection connection = null;
		try {
			connection = super.getOaaConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_COMPENTENCY);
			statement.setInt(1, id);
			statement.execute();
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Compentency compentency = new Compentency();
				compentency.setSkill(rs.getString(1));
				compentency.setSkill_level(rs.getString(2));
				out.add(compentency);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.debug("get compentencies done");
		return out;
	}

	private String format(Date date) {
		if (date != null) {
			return dateFormat.format(date);
		}
		return null;
	}

	public static void main(String[] args) {
		List<Integer> idsList = new LinkedList<Integer>();
		idsList.add(126);
		List<Employee> Employees = new EmployeeDao().getEmployeesFulldata(idsList);
		System.out.println(Employees);
	}
}
