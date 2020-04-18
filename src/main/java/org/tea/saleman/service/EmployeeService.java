package org.tea.saleman.service;

import java.util.List;

import org.tea.saleman.domain.Employee;

public interface EmployeeService {
	List<Employee> listAll();
	Employee findById(int id);
	Employee add(Employee employee);
	Employee update(int id, Employee updatedEmployee);
	void delete(int id);
}
