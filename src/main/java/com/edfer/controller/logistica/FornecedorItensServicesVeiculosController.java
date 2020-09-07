package com.edfer.controller.logistica;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edfer.model.logistica.FornecedorItensServicosVeiculos;
import com.edfer.service.FornecedorItensServicosVeiculosService;

@Controller
@RequestMapping(value = "fornecedor-veiculo")
@CrossOrigin(value = "*")
@PreAuthorize("hasAnyRole('GESTOR')")
public class FornecedorItensServicesVeiculosController {

	@Autowired
	private FornecedorItensServicosVeiculosService service;

	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody FornecedorItensServicosVeiculos fornecedor,
			BindingResult result) {
		service.salvar(fornecedor);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/list")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(service.findAll());
	}
}
