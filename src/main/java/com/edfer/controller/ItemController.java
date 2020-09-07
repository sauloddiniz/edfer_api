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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edfer.model.Item;
import com.edfer.service.ItemService;

@RequestMapping(value="item")
@RestController
@CrossOrigin("*")
@PreAuthorize("hasAnyRole('GESTOR')")
public class ItemController {

	@Autowired
	private ItemService service;
	
	@GetMapping
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@PostMapping
	public ResponseEntity<?> salvarItem(
			@Valid @RequestBody Item item,
			BindingResult result) {

		validItem(item, result);
		if (result.hasErrors()) {
			List<String> error = new ArrayList<>();
			result.getAllErrors().forEach(err -> error.add( err.getDefaultMessage() ));
			System.out.println(result);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
		}
		
		service.salvar(item);

		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/"+item.getIdItem().toString())
				.buildAndExpand()
				.toUri();

		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping(value="/{idItem}")
	public ResponseEntity<?> findByIdFornecedor(@PathVariable("idItem") Long idItem) {
		return ResponseEntity.ok(service.findById(idItem));
	}
	
	@DeleteMapping(value="/{idItem}")
	public ResponseEntity<?> deleteFornecedorById(@PathVariable("idItem") Long idItem) {
		service.delete(idItem);
		return ResponseEntity.noContent().build();
	}
	
	private void validItem(Item item, BindingResult result) {
		service.findByNome(item.getNome()).ifPresent(obj -> {
			if (obj.getIdItem() != item.getIdItem()) {
				result.addError(new ObjectError("Error", "Nome já cadastrado"));
			}
		});
	}
}
