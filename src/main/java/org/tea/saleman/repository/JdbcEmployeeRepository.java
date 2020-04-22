package org.tea.saleman.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.tea.saleman.domain.Employee;

@Repository
public class JdbcEmployeeRepository implements EmployeeRepository {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcEmployeeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Employee> listAll() {
		final String SELECT_ALL_EMPLOYEES = "SELECT employee_id, fullname, title "
				+ "FROM employee WHERE del=false";
		return jdbcTemplate.query(SELECT_ALL_EMPLOYEES, this::mapRowToEmployee);
	}
	
	private Employee mapRowToEmployee(ResultSet rs, int rowNum) throws SQLException {
		Employee employee = new Employee();
		employee.setId(rs.getInt("employee_id"));
		employee.setFullname(rs.getString("fullname"));
		employee.setTitle(rs.getString("title"));
		
		return employee;
	}

	@Override
	public Employee findById(int id) {
		final String SELECT_EMPLOYEE_BY_ID = "SELECT employee_id, fullname, title "
				+ "FROM employee WHERE employee_id=? AND del=false";
		return jdbcTemplate.queryForObject(SELECT_EMPLOYEE_BY_ID, this::mapRowToEmployee, id);
				
	}

	@Override
	public Employee add(Employee employee) {
		final String INSERT_NEW_EMPLOYEE = "INSERT INTO employee (fullname, title) "
				+ "VALUES (?, ?)";
		jdbcTemplate.update(INSERT_NEW_EMPLOYEE,
			employee.getFullname(), 
			employee.getTitle());
		return employee;
	}

	@Override
	public Employee update(int id, Employee updatedEmployee) {
		final String UPDATE_EMPLOYEE = "UPDATE employee "
				+ "SET fullname=?, title=? "
				+ "WHERE employee_id=?";
		jdbcTemplate.update(UPDATE_EMPLOYEE,
				updatedEmployee.getFullname(), 
				updatedEmployee.getTitle(), 
				updatedEmployee.getId());
		return updatedEmployee;
	}

	@Override
	public void delete(int id) {
		final String DELETE_EMPLOYEE = "UPDATE employee "
				+ "SET del=true WHERE employee_id=?";
		jdbcTemplate.update(DELETE_EMPLOYEE, id);
		
	}
	
}
