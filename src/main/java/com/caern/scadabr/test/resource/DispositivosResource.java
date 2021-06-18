package com.caern.scadabr.test.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caern.scadabr.test.entities.Dispositivos;
import com.caern.scadabr.test.repositories.DispositivosRepositories;

@RestController
@RequestMapping(value = "/dispositivos")
public class DispositivosResource {

	@Autowired
	private DispositivosRepositories repositorio;
	
	@GetMapping
	public ResponseEntity<List<Dispositivos>> findAll(){
		List<Dispositivos> list = repositorio.findAll();
		
		return ResponseEntity.ok(list);
	}	
}
