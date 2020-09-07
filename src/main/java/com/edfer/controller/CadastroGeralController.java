package com.edfer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edfer.model.enuns.CategoriaHabilitaco;
import com.edfer.model.enuns.CategoriaVeiculo;
import com.edfer.model.enuns.TipoCombustivel;

@RestController
@RequestMapping(value = "geral")
@CrossOrigin("*")
@PreAuthorize("hasAnyRole('GESTOR')")
public class CadastroGeralController {

	@GetMapping(value = "/habilitacao")
	public ResponseEntity<?> getEnumHabilitacao() {
		return ResponseEntity.ok(CategoriaHabilitaco.values());
	}

	@GetMapping(value = "/categoriaveiculo")
	public ResponseEntity<?> getEnumCategoriaVeiculo() {
		return ResponseEntity.ok(CategoriaVeiculo.values());
	}

	@GetMapping(value = "/categoriacombustivel")
	public ResponseEntity<?> getEnumTipoCombustivel() {
		return ResponseEntity.ok(TipoCombustivel.values());
	}

}
