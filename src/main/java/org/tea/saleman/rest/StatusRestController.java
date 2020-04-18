package org.tea.saleman.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tea.saleman.domain.Status;
import org.tea.saleman.repository.StatusRepository;

@RestController
@RequestMapping(value="/rest/status")
public class StatusRestController {
	
	@Autowired
	private StatusRepository statusRepo;
	
	
	@GetMapping(value="/")
	public ResponseEntity<List<Status>> listAll() {
		List<Status> allStatuses = statusRepo.listAll();
		return new ResponseEntity<>(allStatuses, HttpStatus.OK);
	}
}
