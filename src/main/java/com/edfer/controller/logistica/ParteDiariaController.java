package com.edfer.controller.logistica;

import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edfer.model.logistica.ParteDiaria;
import com.edfer.repository.logistica.RotaDiariaRepository;
import com.edfer.service.logistica.ParteDiariaService;

@RestController
@RequestMapping(value = "parte-diaria")
@CrossOrigin(value = "*")
@PreAuthorize("hasAnyRole('LOGISTICA')")
public class ParteDiariaController {

	@Autowired
	private ParteDiariaService service;
	
	@Autowired
	private RotaDiariaRepository rotaRepository;

	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody @Validated ParteDiaria parteDiaria, BindingResult bindingResult) {
		
		verifyError(parteDiaria, bindingResult);

		if (bindingResult.hasErrors()) {
			List<String> erros = new ArrayList<>();
			bindingResult.getAllErrors().forEach(err -> {
				erros.add(err.getDefaultMessage());
			});
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
		}

		service.salvar(parteDiaria);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/id")
				.buildAndExpand(parteDiaria.getIdParteDiaria().toString()).toUri();

		return ResponseEntity.created(location).body(parteDiaria.getIdParteDiaria());
	}

	private void verifyError(ParteDiaria parteDiaria, BindingResult bindingResult) {
		Optional<ParteDiaria> pd = service.findByDataAndVeiculo(parteDiaria.getDataParteDiaria(),
				parteDiaria.getVeiculo().getIdVeiculo(), parteDiaria.getMotorista().getIdFuncionario());
		
		if (pd.isPresent()) {
			DateTimeFormatter localDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			bindingResult.addError(new ObjectError("dataParteDiaria",
					"ParteDiaria já cadastrada: " + localDateFormat.format(parteDiaria.getDataParteDiaria())));
		}

		if (parteDiaria.getVeiculo().getIdVeiculo() == null) {
			bindingResult.addError(new ObjectError("veículo", "Selecione o veículo"));
		}
	}

	@GetMapping(value = "/criteria")
	public ResponseEntity<?> findAll(
			@RequestParam(value = "sortField", defaultValue = "dataParteDiaria", required = false) String sortField,
			@RequestParam(value = "sortOrder", defaultValue = "0", required = false) String sortOrder,
			@RequestParam(value = "first", defaultValue = "", required = false) String first,
			@RequestParam(value = "rows", defaultValue = "", required = false) String rows,
			@RequestParam(value = "veiculos", defaultValue = "", required = false) String veiculos,
			@RequestParam(value = "cliente", defaultValue = "", required = false) String cliente,
			@RequestParam(value = "nota", defaultValue = "", required = false) String nota,
			@RequestParam(value = "dataParteDiariaMin", defaultValue = "", required = false) String dataParteDiariaMin,
			@RequestParam(value = "dataParteDiariaMax", defaultValue = "", required = false) String dataParteDiariaMax) {

		return ResponseEntity.ok(service.findByCriteria(rows, first, sortField, sortOrder, veiculos, dataParteDiariaMin,
				dataParteDiariaMax, cliente, nota));
	}

	@GetMapping(value = "/nomeClienteOuFornecedor")
	public ResponseEntity<?> findAllNomeClienteOuFornecedor(
			@RequestParam(value = "nomeClienteOuFornecedor", required = true, defaultValue = "") String nomeClienteOuFornecedor) {
		return ResponseEntity.ok(rotaRepository.findByNomeClienteOuFornecedor(nomeClienteOuFornecedor));
	}

	@GetMapping(value = "/{idParteDiaria}")
	public ResponseEntity<?> findById(@PathVariable(name = "idParteDiaria", required = true) Long idParteDiaria) {
		return ResponseEntity.ok(service.findByIdParteDiaria(idParteDiaria));
	}

	@PutMapping
	public ResponseEntity<?> updateParteDiaria(@RequestBody @Validated ParteDiaria parteDiaria, BindingResult bindingResult) {
		service.updateParteDiaria(parteDiaria);
		return ResponseEntity.noContent().build();
	}
}
