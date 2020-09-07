package com.edfer.controller.logistica;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edfer.model.logistica.Veiculo;
import com.edfer.service.logistica.VeiculoService;

@RequestMapping(value = "veiculo")
@RestController
@CrossOrigin("*")
@PreAuthorize("hasAnyRole('LOGISTICA')")
public class VeiculoController {

	@Autowired
	private VeiculoService service;

	@GetMapping
	public ResponseEntity<?> findAll(
			@RequestParam(name = "filter", required = false, defaultValue = "no") String filter) {
		return filter.equals("yes") ? ResponseEntity.ok(service.findAllFilterInit())
				: ResponseEntity.ok(service.findAll());
	}

	@PreAuthorize("hasAnyRole('GESTOR')")
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody Veiculo veiculo, BindingResult result) {

		System.out.println("Veiculo: " + veiculo.toString());

		validVeiculo(veiculo, result);

		if (result.hasErrors()) {
			List<String> error = new ArrayList<>();
			result.getAllErrors().forEach(err -> error.add(err.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
		}

		service.salvar(veiculo);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + veiculo.getIdVeiculo().toString())
				.buildAndExpand().toUri();

		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('GESTOR')")
	@PutMapping(value = "/{idVeiculo}")
	public ResponseEntity<?> atualizar(@PathVariable(value = "idVeiculo") Long idVeiculo,
			@Valid @RequestBody Veiculo veiculo) {
		service.update(veiculo);
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("hasAnyRole('GESTOR')")
	@PatchMapping(value = "/{idVeiculo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> alterarEstado(@PathVariable(value = "idVeiculo") Long idVeiculo,
			@RequestBody Veiculo veiculo) {

		return ResponseEntity.ok().build();
	}

	@PreAuthorize("hasAnyRole('GESTOR')")
	@DeleteMapping(value = "/{idVeiculo}")
	public ResponseEntity<?> delete(@PathVariable(value = "idVeiculo") Long idVeiculo) {
		service.delete(idVeiculo);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/eixos/{idVeiculo}")
	public ResponseEntity<?> getNumeroDeEixos(@PathVariable(value = "idVeiculo") Long idVeiculo) {
		return ResponseEntity.ok(service.getNumeroDeEixos(idVeiculo));
	}

	@GetMapping(value="/{idVeiculo}")
	public ResponseEntity<?> getVeiculoById(@PathVariable(value="idVeiculo") Long idVeiculo) {
		return ResponseEntity.of(service.findById(idVeiculo));
	}

	private void validVeiculo(Veiculo veiculo, BindingResult result) {
		service.findByPlaca(veiculo.getPlaca()).ifPresent(obj -> {
			if (obj.getIdVeiculo() != veiculo.getIdVeiculo()) {
				result.addError(new ObjectError("Error", "Placa já cadastrado"));
			}
		});
	}
}
