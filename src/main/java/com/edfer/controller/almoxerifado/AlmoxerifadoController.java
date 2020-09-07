package com.edfer.controller.almoxerifado;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edfer.model.almoxerifado.EntradaSaidaMiudeza;
import com.edfer.model.almoxerifado.Miudeza;
import com.edfer.model.filtersDTO.FieldToJsonDTO;
import com.edfer.model.filtersDTO.FilterDTO;
import com.edfer.service.almoxerifado.EntradaSaidaService;
import com.edfer.service.almoxerifado.MiudezaService;

@RestController
@RequestMapping(value = "almoxerifado")
@CrossOrigin(value = "*")
public class AlmoxerifadoController {

	@Autowired
	private MiudezaService service;

	@Autowired
	private EntradaSaidaService entradaSaidaMiudezaService;

	@PreAuthorize("hasAnyRole('LOGISTICA')")
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody Miudeza miudeza, BindingResult bindingResult) {

		if (service.findOneByCodigo(miudeza.getCodigo()).isPresent()) {
			bindingResult.addError(new FieldError("miudeza", "codigo", "Código já cadastrado"));
		}

		if (bindingResult.hasErrors()) {
			List<FieldToJsonDTO> listErr = bindingResult.getFieldErrors().parallelStream()
					.map(err -> new FieldToJsonDTO(err.getField(), err.getDefaultMessage()))
					.collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(listErr);
		}

		service.save(miudeza);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping(value = "/criterio")
	public ResponseEntity<?> findAllByCriterio(
			@RequestParam(value = "rows", required = false, defaultValue = "") String rows,
			@RequestParam(value = "first", required = false, defaultValue = "") String first,
			@RequestParam(value = "sortField", required = false, defaultValue = "codigo") String sortField,
			@RequestParam(value = "sortOrder", required = false, defaultValue = "1") String sortOrder,
			@RequestParam(value = "codigo", required = false, defaultValue = "") String codigo,
			@RequestParam(value = "descricao", required = false, defaultValue = "") String descricao) {
		
		Optional<FilterDTO> obj = Optional.of(service.findAllByCriterio(first, rows, sortField, sortOrder, codigo, descricao));

		return obj.isPresent() ? ResponseEntity.ok(obj) : ResponseEntity.notFound().build();
	}

	@GetMapping(value = "miudeza/{codigo}")
	public ResponseEntity<?> findByStartingWith(@PathVariable(value = "codigo") String codigo) {
		List<?> list = service.findAllByCodigoStartingWith(codigo);
		return !list.isEmpty() ? ResponseEntity.ok(list) : ResponseEntity.notFound().build();
	}

	@PreAuthorize("hasAnyRole('LOGISTICA')")
	@PostMapping(value = "/entradaSaida")
	public ResponseEntity<?> saveEntradaSaida(@Valid @RequestBody EntradaSaidaMiudeza entradaSaidaMiudeza,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			List<FieldToJsonDTO> errors = bindingResult.getFieldErrors().parallelStream()
					.map(err -> new FieldToJsonDTO(err.getField(), err.getDefaultMessage()))
					.collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errors);
		}

		entradaSaidaMiudezaService.save(entradaSaidaMiudeza);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/id")
				.buildAndExpand(entradaSaidaMiudeza.getIdEntradaSaida().toString()).toUri();

		Optional<Miudeza> miudeza = 
				service.findById(entradaSaidaMiudeza.getMiudeza().getIdMiudeza());

		if (miudeza.isPresent()) {
			if (miudeza.get().getEstoque() <= miudeza.get().getEstoqueMinimo()) {
				return ResponseEntity.created(location).body(miudeza.get().getEstoque());
			} else {
				return ResponseEntity.created(location).build();				
			}
		}
		return null;
	}

	@GetMapping
	public ResponseEntity<?> findAll() {
		List<?> list = entradaSaidaMiudezaService.findAll();
		return !list.isEmpty() ? ResponseEntity.ok(list) : ResponseEntity.notFound().build();
	}

	@GetMapping(value ="/update/{idEntradaSaida}")
	public ResponseEntity<?> cancelAction(@PathVariable(value = "idEntradaSaida") Long idEntradaSaida) {
		entradaSaidaMiudezaService.update(idEntradaSaida);
		return ResponseEntity.noContent().build();
	}
}
