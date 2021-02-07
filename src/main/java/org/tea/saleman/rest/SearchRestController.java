package org.tea.saleman.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tea.saleman.domain.Invoice;
import org.tea.saleman.domain.QueryDate;
import org.tea.saleman.service.InvoiceService;

@RestController
@RequestMapping(value="/rest/search")
public class SearchRestController {
	
	@Autowired
	private InvoiceService invoiceService;
	
	
	@PostMapping(value="/invoice", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Invoice>> listByDate(
			@RequestBody final QueryDate queryDate) {
		return new ResponseEntity<>(invoiceService.listByDate(queryDate.getFromdate(), queryDate.getTilldate()), HttpStatus.OK);
	}
	
	
}
