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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edfer.model.logistica.FabricanteVeiculo;
import com.edfer.service.logistica.FabricanteVeiculoService;

@RequestMapping(value = "fabricante")
@RestController
@CrossOrigin("*")
@PreAuthorize("hasAnyRole('GESTOR')")
public class FabricanteVeiculoController {

	@Autowired
	private FabricanteVeiculoService service;

	@GetMapping
	public ResponseEntity<?> getAllfabricantes() {
		return ResponseEntity.ok(service.findAll());
	}

	@PostMapping
	public ResponseEntity<?> Salvarfabricante(@Valid @RequestBody FabricanteVeiculo fabricante, BindingResult result) {

		System.out.println("CHEGOU");
		
		validFabricante(fabricante, result);
		if (result.hasErrors()) {
			List<String> error = new ArrayList<>();
			result.getAllErrors().forEach(err -> error.add(err.getDefaultMessage()));
			System.out.println(result);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
		}

		service.salvar(fabricante);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + fabricante.getIdFabricante().toString())
				.buildAndExpand().toUri();

		return ResponseEntity.created(uri).build();
	}

	@GetMapping(value = "/{idFabricante}")
	public ResponseEntity<?> findByIdFornecedor(@PathVariable("idFabricante") Long idFabricante) {
		return ResponseEntity.ok(service.findById(idFabricante));
	}

	@DeleteMapping(value = "/{idFabricante}")
	public ResponseEntity<?> deleteFornecedorById(@PathVariable("idFabricante") Long idFabricante) {
		service.delete(idFabricante);
		return ResponseEntity.noContent().build();
	}

	private void validFabricante(FabricanteVeiculo fabricante, BindingResult result) {
		service.findByNome(fabricante.getNome()).ifPresent(obj -> {
			if (obj.getIdFabricante() != fabricante.getIdFabricante()) {
				result.addError(new ObjectError("Error", "Nome já cadastrado"));
			}
		});
	}

}
