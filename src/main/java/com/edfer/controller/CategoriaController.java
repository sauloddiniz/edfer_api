package com.edfer.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.edfer.model.Categoria;
import com.edfer.service.CategoriaService;

@RequestMapping(value="categoria")
@RestController
@CrossOrigin("*")
public class CategoriaController {

	@Autowired
	private CategoriaService service;

	@GetMapping
	public ResponseEntity<?> getAllCategorias() {
		return ResponseEntity.ok(service.findAll());
	}

	@PostMapping
	public ResponseEntity<?> SalvarCategoria(@Valid @RequestBody Categoria categoria, 
			BindingResult result) {

		validCategoria(categoria, result);
		if (result.hasErrors()) {
			List<String> error = new ArrayList<>();
			result.getAllErrors().forEach(err -> error.add(err.getDefaultMessage()));
			System.out.println(result);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
		}

		service.salvar(categoria);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + categoria.getIdCategoria().toString())
				.buildAndExpand().toUri();

		return ResponseEntity.created(uri).build();
	}

	@GetMapping(value = "/{idCategoria}")
	public ResponseEntity<?> findByIdFornecedor(@PathVariable("idCategoria") Long idCategoria) {
		return ResponseEntity.ok(service.findById(idCategoria));
	}

	@DeleteMapping(value = "/{idCategoria}")
	public ResponseEntity<?> deleteFornecedorById(@PathVariable("idCategoria") Long idCategoria) {
		service.delete(idCategoria);
		return ResponseEntity.noContent().build();
	}

	private void validCategoria(Categoria categoria, BindingResult result) {
		service.findByNome(categoria.getNome()).ifPresent(obj -> {
			if (obj.getIdCategoria() != categoria.getIdCategoria()) {
				result.addError(new ObjectError("Error", "Nome já cadastrado"));
			}
		});
	}

}
