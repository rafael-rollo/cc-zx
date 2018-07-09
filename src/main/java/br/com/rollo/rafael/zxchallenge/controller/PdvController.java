package br.com.rollo.rafael.zxchallenge.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rollo.rafael.zxchallenge.controller.dto.PdvDto;
import br.com.rollo.rafael.zxchallenge.model.Pdv;
import br.com.rollo.rafael.zxchallenge.repository.PdvRepository;

@RestController
@RequestMapping("/api/pdv")
public class PdvController {

	@Autowired
	private PdvRepository pdvRepository;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pdv> create(@Valid @RequestBody PdvDto pdvDto) {
		
		Pdv createdPdv = pdvRepository.save(pdvDto.toPdv());
		return new ResponseEntity<Pdv>(createdPdv, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pdv> getPdvBy(@PathVariable Integer id) {
	
		Optional<Pdv> posssiblePdv = pdvRepository.findById(id);
		
		if(! posssiblePdv.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body(posssiblePdv.get());			
	}
	
}