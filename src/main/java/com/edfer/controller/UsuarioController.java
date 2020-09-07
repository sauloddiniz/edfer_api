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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edfer.model.Usuario;
import com.edfer.service.UsuarioService;

@RestController
@RequestMapping(value = "/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> salvar(@Valid @RequestBody Usuario usuario, BindingResult result) {

		usuarioValid(usuario, result);

		if (result.hasErrors()) {
			List<String> erros = new ArrayList<>();
			result.getAllErrors().forEach(err -> {
				erros.add(err.getDefaultMessage());
			});
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(erros);
		}

		service.salvar(usuario);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/id")
				.buildAndExpand(usuario.getIdUsuario()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping(value = "/{idUsuario}")
	public ResponseEntity<?> updateUser(@PathVariable(name = "idUsuario") Long idUsuario,
			@Valid @RequestBody Usuario usuario) {
		service.update(idUsuario, usuario);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/{idUsuario}")
	public ResponseEntity<?> findById(@PathVariable(name = "idUsuario", required = true) Long idUsuario) {
		return ResponseEntity.ok(service.findById(idUsuario).get());
	}

	@GetMapping
	public ResponseEntity<?> findAllUsers() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping(value = "/vendedores")
	public ResponseEntity<?> findAllByVendas() {
		return ResponseEntity.ok(service.findAllByPerfilVendas());
	}

	private void usuarioValid(Usuario usuario, BindingResult result) {
		service.findByEmail(usuario.getEmail()).ifPresent(obj -> {
			if (obj.getIdUsuario() != usuario.getIdUsuario())
				result.addError(new ObjectError("error-email", "E-mail já cadastrado"));
		});
	}
}
