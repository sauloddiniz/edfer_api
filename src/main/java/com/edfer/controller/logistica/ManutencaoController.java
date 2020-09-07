package com.edfer.controller.logistica;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edfer.model.enuns.TipoServico;
import com.edfer.model.logistica.Manutencao;
import com.edfer.service.logistica.ManutencaoService;

@RequestMapping(value = "manutencao")
@RestController
@CrossOrigin(value = "*")
@PreAuthorize("hasAnyRole('LOGISTICA')")
public class ManutencaoController {

	@Autowired
	private ManutencaoService service;

	@PreAuthorize("hasAnyRole('GESTOR')")
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody Manutencao manutencao, BindingResult result) {

		service.salvar(manutencao);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + manutencao.getIdManutencao().toString())
				.buildAndExpand().toUri();

		return ResponseEntity.created(uri).build();
	}

	@GetMapping
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping(value = "/{idVeiculo}")
	public ResponseEntity<?> findAllByIdVeiculo(@PathVariable(value = "idVeiculo") Long idVeiculo) {
		System.out.println("id::" + idVeiculo);
		return ResponseEntity.ok(service.findAllByIdVeiculo(idVeiculo));
	}

	@GetMapping(value = "/one/{idManutencao}")
	public ResponseEntity<?> findOneById(
			@PathVariable(value = "idManutencao") Long idVeiculo) {
		return ResponseEntity.ok(service.findById(idVeiculo));
	}

	@GetMapping(value = "/fornecedor")
	public ResponseEntity<?> getAllFornecedores(
			@RequestParam(value = "fornecedor", required = true, defaultValue = "") String fornecedor) {
		return ResponseEntity.ok(service.findAllForncedorStartingWith(fornecedor));
	}

	@PreAuthorize("hasAnyRole('GESTOR')")
	@DeleteMapping(value = "/{idManutencao}")
	public ResponseEntity<?> removeById(@PathVariable(value = "idManutencao") Long idManutencao) {
		service.delete(idManutencao);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('GESTOR')")
	@PutMapping(value = "/{idManutencao}")
	public ResponseEntity<?> update(
			@PathVariable(value = "idManutencao") Long idManutencao,
			@Valid @RequestBody Manutencao manutencao, BindingResult bindingResult) {
		service.update(idManutencao, manutencao);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/criteria")
	public ResponseEntity<?> getAllByCriteria(
			@RequestParam(value = "sortField", required = false, defaultValue = "idManutencao") String sortField,
			@RequestParam(value = "sortOrder", required = false, defaultValue = "0") String sortOrder,
			@RequestParam(value = "rows", required = false, defaultValue = "") String rows,
			@RequestParam(value = "first", required = false, defaultValue = "") String first,
			@RequestParam(value = "idManutencao", required = false, defaultValue = "") String idManutencao,
			@RequestParam(value = "tipoManutencao", required = false, defaultValue = "") String tipoManutencao,
			@RequestParam(value = "tipoServico", required = false, defaultValue = "") String tipoServico,
			@RequestParam(value = "valorMin", required = false, defaultValue = "") String valorMin,
			@RequestParam(value = "valorMax", required = false, defaultValue = "") String valorMax,
			@RequestParam(value = "tipoNota", required = false, defaultValue = "") String tipoNota,
			@RequestParam(value = "validHodometroMin", required = false, defaultValue = "") String validHodometroMin,
			@RequestParam(value = "validHodometroMax", required = false, defaultValue = "") String validHodometroMax,
			@RequestParam(value = "validManutencaoDateMin", required = false, defaultValue = "") String validManutencaoDateMin,
			@RequestParam(value = "validManutencaoDateMax", required = false, defaultValue = "") String validManutencaoDateMax,
			@RequestParam(value = "fornecedor", required = false, defaultValue = "") String fornecedor,
			@RequestParam(value = "numNotaOuNumOrdem", required = false, defaultValue = "") String numNotaOuNumOrdem,
			@RequestParam(value = "veiculos", required = false, defaultValue = "") String veiculos) {

		return ResponseEntity.ok(service.findAllByCriteria(sortField, sortOrder, first, rows, idManutencao,
				tipoManutencao, tipoServico, valorMin, valorMax, tipoNota, validHodometroMin, validHodometroMax, validManutencaoDateMin,
				validManutencaoDateMax, fornecedor, numNotaOuNumOrdem, veiculos));
	}

	@GetMapping(value="/getServicos")
	public ResponseEntity<?> getAllServicos() {
		return ResponseEntity.ok(TipoServico.values());
	}
}
