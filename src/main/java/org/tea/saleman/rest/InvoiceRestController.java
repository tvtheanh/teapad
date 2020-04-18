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
import org.tea.saleman.domain.Invoice;
import org.tea.saleman.service.InvoiceService;

@RestController
@RequestMapping(value="/rest/invoice")
public class InvoiceRestController {
	
	private InvoiceService invoiceService;
	
	@Autowired
	public InvoiceRestController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}
	
	@GetMapping(value="/")
	public ResponseEntity<List<Invoice>> listAll() {
		return new ResponseEntity<>(invoiceService.listAll(), HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Invoice> findById(@PathVariable int id) {
		return new ResponseEntity<>(invoiceService.findById(id), HttpStatus.OK);
	}
	
	@PostMapping(value="/", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> add(@RequestBody final Invoice invoice) {
		return new ResponseEntity<>(invoiceService.add(invoice), HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Invoice> update(
			@PathVariable int id, @RequestBody final Invoice invoice) {
		return new ResponseEntity<>(invoiceService.update(id, invoice), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		invoiceService.delete(id);
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}
}
