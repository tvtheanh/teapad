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
import org.tea.saleman.domain.Giveaway;
import org.tea.saleman.repository.GiveawayRepository;

@RestController
@RequestMapping(value="/rest/giveaway")
public class GiveawayRestController {
	
	@Autowired
	private GiveawayRepository giveawayRepo;
	
	@GetMapping(value="/")
	public ResponseEntity<List<Giveaway>> listAll() {
		List<Giveaway> allGiveaways = giveawayRepo.listAll();
		return new ResponseEntity<>(allGiveaways, HttpStatus.OK);
	}

	@GetMapping(value="/{id}")
	public ResponseEntity<Giveaway> getById(@PathVariable("id") int id) {
		Giveaway giveaway = giveawayRepo.findById(id);
		return new ResponseEntity<>(giveaway, HttpStatus.OK);
	}
	
	@PostMapping(value="/", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Giveaway> create(@RequestBody final Giveaway newGiveaway) {
		giveawayRepo.add(newGiveaway);
		return new ResponseEntity<>(newGiveaway, HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Giveaway> update(@PathVariable("id") int id, 
			@RequestBody final Giveaway editedGiveaway) {
		Giveaway giveaway = giveawayRepo.update(id, editedGiveaway);
		return new ResponseEntity<>(giveaway, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		giveawayRepo.delete(id);
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}
}
