package org.tea.saleman.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tea.saleman.domain.Timesheet;
import org.tea.saleman.repository.TimesheetRepository;

@RestController
@RequestMapping(value="/rest/chamcong")
public class TimesheetRestController {
	
	@Autowired
	private TimesheetRepository timesheetRepo;
	
	
	@GetMapping(value="/")
	public ResponseEntity<List<Timesheet>> listAll() {
		List<Timesheet> allTimesheet = timesheetRepo.listAll();
		return new ResponseEntity<List<Timesheet>>(allTimesheet, HttpStatus.OK);
	}
	
	@GetMapping(value="/{month}/{year}")
	public ResponseEntity<List<Timesheet>> listByMonth(@PathVariable String month, @PathVariable String year) {
		List<Timesheet> monthTimesheet = timesheetRepo.listByMonth(month, year);
		return new ResponseEntity<List<Timesheet>>(monthTimesheet, HttpStatus.OK);
	}
	
	@GetMapping(value="")
	public ResponseEntity<?> update(@RequestParam int employee_id, @RequestParam String day, @RequestParam int newValue) {
		timesheetRepo.update(employee_id, day, newValue);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?> delete(@PathVariable int id, @RequestParam String month, @RequestParam String year) {
		timesheetRepo.delete(id, month, year);
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}
}
