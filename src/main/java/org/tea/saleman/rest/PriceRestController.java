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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tea.saleman.domain.Price;
import org.tea.saleman.repository.PriceRepository;

@RestController
@RequestMapping(value="/rest/price")
public class PriceRestController {

	@Autowired
	private PriceRepository priceRepo;
	
	
	@GetMapping(value="/")
	public ResponseEntity<List<Price>> listAll() {
		return new ResponseEntity<>(priceRepo.listAll(), HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Price> findById(@PathVariable int id) {
		return new ResponseEntity<>(priceRepo.findById(id), HttpStatus.OK);
	}
	
	@GetMapping(value="/byproduct")
	public ResponseEntity<List<Price>> findByProduct(@RequestParam int productid) {
		return new ResponseEntity<>(priceRepo.findByProduct(productid), HttpStatus.OK);
	}
	
	@PostMapping(value="/", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Price> add(@RequestBody final Price price) {
		return new ResponseEntity<>(priceRepo.add(price), HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Price> update
		(@PathVariable int id, @RequestBody final Price price) {
		return new ResponseEntity<>(priceRepo.update(id, price), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Price> delete(@PathVariable int id) {
		priceRepo.delete(id);
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}
}
