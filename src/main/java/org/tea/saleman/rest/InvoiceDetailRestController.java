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
import org.tea.saleman.domain.InvoiceDetail;
import org.tea.saleman.service.InvoiceDetailService;

@RestController
@RequestMapping(value="/rest/invoicedetail")
public class InvoiceDetailRestController {
	
	private InvoiceDetailService invoiceDetailService;
	
	@Autowired
	public InvoiceDetailRestController(InvoiceDetailService invoiceDetailService) {
		this.invoiceDetailService = invoiceDetailService;
	}
	
	@GetMapping(value="/byinvoice")
	public ResponseEntity<List<InvoiceDetail>> findByInvoiceId(@RequestParam int invoiceid) {
		return new ResponseEntity<>(invoiceDetailService.findByInvoiceId(invoiceid), HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<InvoiceDetail> findById(@PathVariable int id) {
		return new ResponseEntity<>(invoiceDetailService.findById(id), HttpStatus.OK);
	}
	
	@PostMapping(value="/", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InvoiceDetail> add(@RequestBody final InvoiceDetail invoiceDetail) {
		return new ResponseEntity<>(invoiceDetailService.add(invoiceDetail), HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InvoiceDetail> update(
			@PathVariable int id, @RequestBody final InvoiceDetail invoiceDetail) {
		return new ResponseEntity<>(invoiceDetailService.update(id, invoiceDetail), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		invoiceDetailService.delete(id);
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

}
