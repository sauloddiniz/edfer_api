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

import com.edfer.model.logistica.Posto;
import com.edfer.service.logistica.PostoService;

@RestController
@RequestMapping(value="posto")
@CrossOrigin("*")
@PreAuthorize("hasAnyRole('LOGISTICA')")
public class PostoController {

	@Autowired
	private PostoService service;

	@GetMapping
	public ResponseEntity<?> getAllPostos() {
		return ResponseEntity.ok(service.findAll());
	}

	@PreAuthorize("hasAnyRole('GESTOR')")
	@PostMapping
	public ResponseEntity<?> SalvarPosto(
			@Valid @RequestBody Posto posto,
			BindingResult result) {

		validPosto(posto, result);
		if (result.hasErrors()) {
			List<String> error = new ArrayList<>();
			result.getAllErrors().forEach(err -> error.add( err.getDefaultMessage() ));
			System.out.println(result);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
		}
		
		service.salvar(posto);

		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/"+posto.getIdPosto().toString())
				.buildAndExpand()
				.toUri();

		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping(value="/{idPosto}")
	public ResponseEntity<?> findByIdFornecedor(@PathVariable("idPosto") Long idPosto) {
		return ResponseEntity.ok(service.findById(idPosto));
	}
	
	@PreAuthorize("hasAnyRole('GESTOR')")
	@DeleteMapping(value="/{idPosto}")
	public ResponseEntity<?> deleteFornecedorById(@PathVariable("idPosto") Long idPosto) {
		service.delete(idPosto);
		return ResponseEntity.noContent().build();
	}
	
	private void validPosto(Posto posto, BindingResult result) {
		service.findByNomeIgnoreCase(posto.getNome()).ifPresent(obj -> {
			if (obj.getIdPosto() != posto.getIdPosto()) {
				result.addError(new ObjectError("Error", "Nome já cadastrado"));
			}
		});
	}
	
}
