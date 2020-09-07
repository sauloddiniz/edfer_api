package com.edfer.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edfer.model.FornecedorProduto;
import com.edfer.service.FornecedorProdutoService;

@RequestMapping(value="fornecedor-produto")
@RestController
@CrossOrigin(value="*")
@PreAuthorize("hasAnyRole('GESTOR')")
public class FornecedorProdutoController {

	@Autowired
	private FornecedorProdutoService service;

	@GetMapping
	public ResponseEntity<?> getAllFornecedor(@RequestParam(value="nome", required=false, defaultValue="") String nome ) {
		return nome.equals("") ? ResponseEntity.ok(service.findAll()) 
							   : ResponseEntity.ok(service.findByNomeStartingWith(nome));
	}

	@PostMapping
	public ResponseEntity<?> salvarFornecedor(
			@Valid @RequestBody FornecedorProduto fornecedorProduto,
			BindingResult result) {

		validFornecedor(fornecedorProduto, result);
		if (result.hasErrors()) {
			List<String> error = new ArrayList<>();
			result.getAllErrors().forEach(err -> error.add( err.getDefaultMessage() ));
			System.out.println(result);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
		}
		
		service.salvar(fornecedorProduto);

		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/"+fornecedorProduto.getIdFornecedor().toString())
				.buildAndExpand()
				.toUri();

		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping(value="/{idFornecedorProduto}")
	public ResponseEntity<?> findByIdFornecedor(@PathVariable("idFornecedorProduto") Long idFornecedorProduto) {
		System.out.println(idFornecedorProduto);
		return ResponseEntity.ok(service.findById(idFornecedorProduto));
	}
	
	@DeleteMapping(value="/{idFornecedorProduto}")
	public ResponseEntity<?> deleteFornecedorById(@PathVariable("idFornecedorProduto") Long idFornecedorProduto) {
		service.delete(idFornecedorProduto);
		return ResponseEntity.noContent().build();
	}
	
	private void validFornecedor(FornecedorProduto fornecedor, BindingResult result) {
		service.findByNome(fornecedor.getNome()).ifPresent(obj -> {
			if (obj.getIdFornecedor() != fornecedor.getIdFornecedor()) {
				result.addError(new ObjectError("Error", "Nome já cadastrado"));
			}
		});
	}
}
