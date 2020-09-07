package com.edfer.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edfer.model.PrevisaoChegadaProduto;
import com.edfer.service.PrevisaoChegadaProdutoService;

@RestController
@RequestMapping(value = "previsao")
@CrossOrigin(value = "*")
public class PrevisaoChegadaProdutoController {

	@Autowired
	private PrevisaoChegadaProdutoService service;

	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody PrevisaoChegadaProduto previsaoChegadaProduto) {

		service.salvar(previsaoChegadaProduto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/" + previsaoChegadaProduto.getIdPrevisaoChegadaProduto().toString()).buildAndExpand().toUri();

		return ResponseEntity.created(uri).build();
	}

	@GetMapping(value="/lista")
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(service.findAll());
	}
}
