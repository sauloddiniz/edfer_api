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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edfer.model.logistica.EstadoPneu;
import com.edfer.service.logistica.EstadoPneuService;

@RestController
@RequestMapping(value = "pneu/{idPneu}/estado")
@CrossOrigin(value = "*")
@PreAuthorize("hasAnyRole('LOGISTICA')")
public class EstadoPneuController {

	@Autowired
	private EstadoPneuService estadoService;

	@GetMapping
	public ResponseEntity<?> findAllByPneu(@PathVariable(value = "idPneu") Long idPneu) {
		return ResponseEntity.ok(estadoService.findAllByIdPneu(idPneu));
	}

	@PreAuthorize("hasAnyRole('GESTOR')")
	@PostMapping
	public ResponseEntity<?> salvarEstado(@PathVariable(value = "idPneu") Long idPneu,
			@Valid @RequestBody EstadoPneu estadoPneu, BindingResult result) {

		if (result.hasErrors()) {
			List<String> erros = new ArrayList<>();
			result.getAllErrors().forEach(err -> {
				erros.add(err.getDefaultMessage());
			});
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(erros);
		}

		estadoService.salvar(idPneu, estadoPneu);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/id")
				.buildAndExpand(estadoPneu.getEstado()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PreAuthorize("hasAnyRole('GESTOR')")
	@PutMapping(value="/{idEstadoPneu}")
	public ResponseEntity<?> atualizarEstado(
			@PathVariable(value = "idPneu") Long idPneu,
			@PathVariable(value = "idEstadoPneu") Long idEstadoPneu,
			@RequestBody EstadoPneu estadoPneu) {

		estadoService.update(idPneu, idEstadoPneu, estadoPneu);

		return ResponseEntity.accepted().build();
	}

	@PreAuthorize("hasAnyRole('GESTOR')")
	@DeleteMapping(value = "/{idEstado}")
	public ResponseEntity<?> deleteEstado(
			@PathVariable(value = "idPneu") Long idPneu,
			@PathVariable(value = "idEstado") Long idEstado) {

		return ResponseEntity.noContent().build();
	}


}
