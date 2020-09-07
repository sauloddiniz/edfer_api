package com.edfer.controller.logistica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edfer.model.logistica.Pneu;
import com.edfer.service.logistica.PneuService;

@RequestMapping(value = "pneu")
@RestController
@CrossOrigin(value = "*")
@PreAuthorize("hasAnyRole('LOGISTICA')")
public class PneuController {

	@Autowired
	private PneuService service;

	@PreAuthorize("hasAnyRole('GESTOR')")
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Pneu pneu) {
		System.out.println(pneu.toString());
		service.salvar(pneu);
		return ResponseEntity.ok("");
	}

	@PreAuthorize("hasAnyRole('GESTOR')")
	@PutMapping
	public ResponseEntity<?> updateById(@RequestBody Pneu pneu) {
		service.update(pneu);
		return ResponseEntity.ok("");
	}

	@GetMapping(value="/criterio")
	public ResponseEntity<?> findListByCriterio(
			@RequestParam(value="sortField", defaultValue="idPneu", required= false) String sortField,
			@RequestParam(value="sortOrder", defaultValue="0", required= false) String sortOrder,
			@RequestParam(value="first", defaultValue="", required= false) String first,
			@RequestParam(value="rows", defaultValue="", required= false) String rows,
			@RequestParam(value="veiculos", defaultValue="", required= false) String veiculos,
			@RequestParam(value="codControleEdfer", defaultValue="", required=false) String codControleEdfer,
			@RequestParam(value="kmMin", defaultValue="", required=false) String kmMin,
			@RequestParam(value="kmMax", defaultValue="", required=false) String kmMax,
			@RequestParam(value="compraPneuDateMin", defaultValue="", required=false) String compraPneuDateMin,
			@RequestParam(value="compraPneuDateMax", defaultValue="", required=false) String compraPneuDateMax,
			@RequestParam(value="modelo", defaultValue="", required= false) String modelo,
			@RequestParam(value="fabricantePneu", defaultValue="", required= false) String fabricantePneu,
			@RequestParam(value="estadoAtual", defaultValue="", required= false) String estadoAtual,
			@RequestParam(value="numSerie", defaultValue="", required= false) String numSerie,
			@RequestParam(value="numNotaOuNumOrdem", defaultValue="", required= false) String numNotaOuNumOrdem,
			@RequestParam(value="estepe", defaultValue="", required= false) String estepe,
			@RequestParam(value="ativo", defaultValue="", required= false) String ativo
			) {
		System.out.println("Veiculos: " + veiculos);
		return ResponseEntity.ok(service.findAllByCriteria(sortField, sortOrder, first, rows, veiculos, codControleEdfer, kmMin, kmMax, compraPneuDateMin, compraPneuDateMax, modelo, fabricantePneu, estadoAtual, numSerie, numNotaOuNumOrdem, estepe, ativo));
	}

	@PreAuthorize("hasAnyRole('GESTOR')")
	@PatchMapping(value = "/{idPneu}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> alterarEstado(@PathVariable(value = "idPneu") Long idPneu, @RequestBody Pneu pneu) {

		//service.updateAtivo(pneu.isAtivo(), idPneu);

		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/{idVeiculo}/lista")
	public ResponseEntity<?> findAllByVeiculoId(@PathVariable(value = "idVeiculo") Long idVeiculo) {
		return ResponseEntity.ok(service.findAllByVeiculo(idVeiculo).get());
	}

	@GetMapping(value = "/{idPneu}")
	public ResponseEntity<?> findByIdPneu(@PathVariable(value = "idPneu") Long idPneu) {
		return ResponseEntity.ok(service.findById(idPneu));
	}
}
