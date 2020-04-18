package org.tea.saleman.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tea.saleman.domain.Employee;
import org.tea.saleman.domain.Timesheet;
import org.tea.saleman.repository.EmployeeRepository;
import org.tea.saleman.repository.TimesheetRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	private EmployeeRepository employeeRepository;
	private TimesheetRepository timesheetRepository;
	
	@Autowired
	public EmployeeServiceImpl(EmployeeRepository employeeRepository, TimesheetRepository timesheetRepository) {
		this.employeeRepository = employeeRepository;
		this.timesheetRepository = timesheetRepository;
	}

	@Override
	public List<Employee> listAll() {
		return employeeRepository.listAll();
	}

	@Override
	public Employee findById(int id) {
		return employeeRepository.findById(id);
	}

	@Override
	public Employee add(Employee employee) {
		employeeRepository.add(employee);
		// add new timesheet for each new employee
		LocalDate currentDate = LocalDate.now();
		String month = String.format("%02d", currentDate.getMonthValue());
		String year = String.format("%d", currentDate.getYear());
		Timesheet timesheet = new Timesheet();
		timesheet.setEmployee_id(employee.getId());
		timesheet.setAcc_month(month);
		timesheet.setAcc_year(year);
		timesheetRepository.add(timesheet);
		
		return employee;
	}

	@Override
	public Employee update(int id, Employee updatedEmployee) {
		return employeeRepository.update(id, updatedEmployee);
	}

	@Override
	public void delete(int id) {
		employeeRepository.delete(id);
	}
	
}
