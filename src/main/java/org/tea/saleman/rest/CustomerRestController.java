package org.tea.saleman.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tea.saleman.domain.Customer;
import org.tea.saleman.repository.CustomerRepository;

@RestController
@RequestMapping(value="/rest/customer")
public class CustomerRestController {
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@GetMapping(value="/")
	public ResponseEntity<List<Customer>> listAll() {
		List<Customer> allCustomers = customerRepo.listAll();
		return new ResponseEntity<>(allCustomers, HttpStatus.OK);
	}

	@GetMapping(value="/{id}")
	public ResponseEntity<Customer> getById(@PathVariable("id") int id) {
		Customer customer = customerRepo.findById(id);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
	@PostMapping(value="/", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> create(@RequestBody final Customer newCustomer) {
		customerRepo.add(newCustomer);
		return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> update(@PathVariable("id") int id, 
			@RequestBody final Customer editedCustomer) {
		Customer customer = customerRepo.update(id, editedCustomer);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
}
