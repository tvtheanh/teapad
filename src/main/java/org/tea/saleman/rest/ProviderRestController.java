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
	
	@PostMapping(value="/", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Provider> add(@RequestBody final Provider newProvider) {
		return new ResponseEntity<>(providerRepo.add(newProvider), HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Provider> update(
			@PathVariable int id, @RequestBody final Provider updatedProvider) {
		return new ResponseEntity<>(providerRepo.update(id, updatedProvider), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Provider> delete(@PathVariable int id) {
		providerRepo.delete(id);
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

}
