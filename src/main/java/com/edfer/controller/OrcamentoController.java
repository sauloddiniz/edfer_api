package com.edfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edfer.model.Orcamento;
import com.edfer.service.OrcamentoItemService;
import com.edfer.service.OrcamentoService;

@RestController
@RequestMapping(value = "orcamento")
@CrossOrigin(value = "*")
public class OrcamentoController {

	@Autowired
	private OrcamentoService service;
	
	@Autowired
	private OrcamentoItemService orcamentoItemService;

	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Orcamento orcamento) {
		System.out.println(orcamento.toString());
		service.salvar(orcamento);
		return ResponseEntity.ok("");
	}

	@GetMapping
	ResponseEntity<?> getAll() {
		return ResponseEntity.ok(orcamentoItemService.findAll());
	}
}
