package org.tea.saleman.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tea.saleman.domain.Employee;
import org.tea.saleman.service.EmployeeService;

@RestController
@RequestMapping(value="/rest/employee")
public class EmployeeRestController {
	
	private EmployeeService employeeService;
	
	@Autowired
	public EmployeeRestController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@GetMapping(value="/")
	public ResponseEntity<List<Employee>> listAll() {
		List<Employee> allEmployees = employeeService.listAll();
		return new ResponseEntity<>(allEmployees, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Employee> getById(@PathVariable int id) {
		Employee employee = employeeService.findById(id);
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}
	
	@PostMapping(value="/", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> create(@RequestBody final Employee employee) {
		employeeService.add(employee);
		return new ResponseEntity<>(employee, HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> update(@PathVariable int id,
			@RequestBody final Employee updatedEmployee) {
		Employee employee = employeeService.update(id, updatedEmployee);
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		employeeService.delete(id);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
}
