package org.tea.saleman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tea.saleman.repository.JdbcCustomerRepository;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	JdbcCustomerRepository customerRepository;
	
	@GetMapping
	public String listCustomer(Model model) {
		model.addAttribute("customers", customerRepository.listAll());
		return "customer";
	}
	
	@GetMapping(value="/{id}")
	public String findCustomerById(@PathVariable("id") long id, Model model) {
		return "customer";
	}
}
