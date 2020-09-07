package com.edfer.controller.logistica;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edfer.model.logistica.Ajudante;
import com.edfer.model.logistica.Motorista;
import com.edfer.service.LogisticaFuncionarioService;
import com.edfer.service.logistica.AjudanteService;
import com.edfer.service.logistica.MotoristaService;

@CrossOrigin("*")
@RequestMapping(value = "colaborador")
@RestController
@PreAuthorize("hasAnyRole('LOGISTICA')")
public class FuncionarioController {

	@Autowired
	private MotoristaService serviceMotorista;
	
	@Autowired
	private AjudanteService serviceAjudante;
	
	@Autowired
	private LogisticaFuncionarioService serviceLogistaFuncionario;
	
	@PostMapping(value = "/motorista")
	public ResponseEntity<?> salvar(@Valid @RequestBody Motorista motorista, BindingResult result ) {

		if (result.hasErrors()) {
			List<String> erros = new ArrayList<>();
			result.getAllErrors().forEach(err -> {
				erros.add(err.getDefaultMessage());
			});
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
		}

		serviceMotorista.salvar(motorista);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/id")
				.buildAndExpand(motorista.getIdFuncionario().toString()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PostMapping(value = "/ajudante")
	public ResponseEntity<?> salvarAjudante(@Valid @RequestBody Ajudante ajudante, BindingResult result ) {

		if (result.hasErrors()) {
			List<String> erros = new ArrayList<>();
			result.getAllErrors().forEach(err -> {
				erros.add(err.getDefaultMessage());
			});
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
		}

		serviceAjudante.salvar(ajudante);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/id")
				.buildAndExpand(ajudante.getIdFuncionario().toString()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping(value = "/lista")
	public ResponseEntity<?> findAllisAtivo() {
		return ResponseEntity.ok(serviceLogistaFuncionario.findAll());
	}
	
	@GetMapping(value = "/ajudantes")
	public ResponseEntity<?> findAllAjudanteIsAtivo() {
		return ResponseEntity.ok(serviceAjudante.findAllisAtivo());
	}
	
	@GetMapping(value = "/motoristas")
	public ResponseEntity<?> findAllMotorisaIsAtivo() {
		return ResponseEntity.ok(serviceMotorista.findAllIsAtivo());
	}

	@GetMapping(value = "/{idFuncionario}")
	public ResponseEntity<?> updateFuncionario(
			@PathVariable(name = "idFuncionario", required = true) Long idFuncionario) {
		return ResponseEntity.ok(serviceLogistaFuncionario.findById(idFuncionario));
	}

}
