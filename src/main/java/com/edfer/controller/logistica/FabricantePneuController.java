package com.edfer.controller.logistica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edfer.model.logistica.FabricantePneu;
import com.edfer.service.logistica.FabricantePneuService;

@RestController
@RequestMapping(value="fabricante-pneu")
@CrossOrigin
@PreAuthorize("hasAnyRole('GESTOR')")
public class FabricantePneuController {

	@Autowired
	private FabricantePneuService service;
	
	@PostMapping
	public ResponseEntity<?> salvarFabricante(@RequestBody FabricantePneu fabricantePneu){
		service.salvar(fabricantePneu);
		return ResponseEntity.ok("");
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(service.findAll());
	}
}
