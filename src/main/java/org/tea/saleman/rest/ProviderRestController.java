package org.tea.saleman.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tea.saleman.domain.Provider;
import org.tea.saleman.repository.ProviderRepository;

@RestController
@RequestMapping(value="/rest/provider")
public class ProviderRestController {
	
	@Autowired
	private ProviderRepository providerRepo;
	
	
	@GetMapping(value="/")
	public ResponseEntity<List<Provider>> listAll() {
		return new ResponseEntity<>(providerRepo.listAll(), HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Provider> findById(@PathVariable int id) {
		return new ResponseEntity<>(providerRepo.findById(id), HttpStatus.OK);
	}

}
