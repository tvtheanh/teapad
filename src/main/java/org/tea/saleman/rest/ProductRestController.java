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
import org.tea.saleman.domain.Product;
import org.tea.saleman.repository.ProductRepository;

@RestController
@RequestMapping(value="/rest/product")
public class ProductRestController {
	
	@Autowired
	private ProductRepository productRepo;
	
	
	@GetMapping(value="/")
	public ResponseEntity<List<Product>> listAll() {
		return new ResponseEntity<>(productRepo.listAll(), HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Product> findById(@PathVariable int id) {
		return new ResponseEntity<>(productRepo.findById(id), HttpStatus.OK);
	}
	
	@PostMapping(value="/", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> add(@RequestBody final Product product) {
		return new ResponseEntity<>(productRepo.add(product), HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> update(
			@PathVariable int id, @RequestBody final Product product) {
		return new ResponseEntity<>(productRepo.update(id, product), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		productRepo.delete(id);
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}
}
