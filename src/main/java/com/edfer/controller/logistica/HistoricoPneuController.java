package com.edfer.controller.logistica;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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

import com.edfer.model.logistica.HistoricoPneu;
import com.edfer.service.logistica.HistoricoPneuService;

@RestController
@RequestMapping(value = "pneu/{idPneu}/historico")
@CrossOrigin(value = "*")
@PreAuthorize("hasAnyRole('LOGISTICA')")
public class HistoricoPneuController {

	@Autowired
	private HistoricoPneuService historicoService;

	@GetMapping
	public ResponseEntity<?> findAllByPneu(@PathVariable(value = "idPneu") Long idPneu) {
		return ResponseEntity.ok(historicoService.findAllByIdPneu(idPneu));
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('GESTOR')")
	public ResponseEntity<?> salvarHistorico(@PathVariable(value = "idPneu") Long idPneu,
			@RequestBody HistoricoPneu historicoPneu, BindingResult bindingResult) {

		System.out.println("Chegou aquiiiii");
		
		if (bindingResult.hasErrors()) {
			List<String> erros = new ArrayList<>();
			bindingResult.getAllErrors().forEach(err -> {
				erros.add(err.getDefaultMessage());
			});
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(erros);
		}
		
		historicoService.salvar(historicoPneu);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/id")
				.buildAndExpand(historicoPneu.getIdHistorico()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{idHistorico}")
	@PreAuthorize("hasAnyRole('GESTOR')")
	public ResponseEntity<?> updateHistorico(
			@PathVariable(value = "idPneu") Long idPneu,
			@PathVariable(value = "idHistorico") Long idHistorico,
			@RequestBody HistoricoPneu historicoPneu) {

		historicoService.update(idHistorico, historicoPneu);

		return ResponseEntity.ok("");
	}

	@PreAuthorize("hasAnyRole('GESTOR')")
	@DeleteMapping(value = "/{idHistorico}")
	public ResponseEntity<?> deleteHistorico(@PathVariable(value = "idPneu") Long idPneu,
			@PathVariable(value = "idHistorico") Long idHistorico) {

		historicoService.delete(idPneu, idHistorico);

		return ResponseEntity.noContent().build();
	}

}
