package com.edfer.controller.agenda;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edfer.model.agenda.Agenda;
import com.edfer.model.enuns.StatusGender;
import com.edfer.service.agenda.AgendaService;
import com.edfer.service.agenda.GenderDateService;

@RequestMapping(value = "agenda")
@RestController
@CrossOrigin(origins = "*")
@PreAuthorize("hasAnyRole('VENDA')")
public class AgendaController {

	@Autowired
	private AgendaService service;

	@Autowired
	private GenderDateService genderDateService;

	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody Agenda agenda, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			List<String> erros = new ArrayList<>();
			bindingResult.getAllErrors().forEach(err -> {
				erros.add(err.getDefaultMessage());
			});
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(erros);
		}

		service.salvar(agenda);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(agenda.getIdAgenda())
				.toUri();

		return ResponseEntity.created(location).body(agenda.getIdAgenda());
	}

	@GetMapping(value = "/list/vendedor")
	public ResponseEntity<?> findAllByUsuarioEmail(
			@RequestParam(value = "rows", required = false, defaultValue = "") String rows,
			@RequestParam(value = "first", required = false, defaultValue = "") String first,
			@RequestParam(value = "idAgenda", required = false, defaultValue = "") String idAgenda,
			@RequestParam(value = "estados", required = false, defaultValue = "") String estados,
			@RequestParam(value = "tipoCliente", required = false, defaultValue = "") String tipoCliente,
			@RequestParam(value = "cliente", required = false, defaultValue = "") String cliente,
			@RequestParam(value = "visitaDe", required = false, defaultValue = "") String visitaDe,
			@RequestParam(value = "visitaAte", required = false, defaultValue = "") String visitaAte,
			@RequestParam(value = "retornoDe", required = false, defaultValue = "") String retornoDe,
			@RequestParam(value = "retornoAte", required = false, defaultValue = "") String retornoAte,
			@RequestParam(value = "observacao", required = false, defaultValue = "") String observacao,
			@RequestParam(value = "orcamentoNumber", required = false, defaultValue = "") String orcamentoNumber,
			@RequestParam(value = "sortField", required = false, defaultValue = "idAgenda") String sortField,
			@RequestParam(value = "sortOrder", required = false, defaultValue = "0") String sortOrder,
			@RequestParam(value = "valorMin", required = false, defaultValue = "") String valorMin,
			@RequestParam(value = "valorMax", required = false, defaultValue = "") String valorMax) {

		return ResponseEntity.ok(service.findAllCriteriaByVendedor(first, rows, idAgenda, estados, tipoCliente, cliente,
				visitaDe, visitaAte, retornoDe, retornoAte, observacao, sortField, sortOrder, orcamentoNumber, valorMin,
				valorMax));
	}

	@GetMapping(value = "/list")
	@PreAuthorize("hasAnyRole('GESTOR')")
	public ResponseEntity<?> findAllByCriteria(
			@RequestParam(value = "rows", required = false, defaultValue = "") String rows,
			@RequestParam(value = "first", required = false, defaultValue = "") String first,
			@RequestParam(value = "nomes", required = false, defaultValue = "") String nomes,
			@RequestParam(value = "idAgenda", required = false, defaultValue = "") String idAgenda,
			@RequestParam(value = "estados", required = false, defaultValue = "") String estados,
			@RequestParam(value = "tipoCliente", required = false, defaultValue = "") String tipoCliente,
			@RequestParam(value = "cliente", required = false, defaultValue = "") String cliente,
			@RequestParam(value = "visitaDe", required = false, defaultValue = "") String visitaDe,
			@RequestParam(value = "visitaAte", required = false, defaultValue = "") String visitaAte,
			@RequestParam(value = "retornoDe", required = false, defaultValue = "") String retornoDe,
			@RequestParam(value = "retornoAte", required = false, defaultValue = "") String retornoAte,
			@RequestParam(value = "observacao", required = false, defaultValue = "") String observacao,
			@RequestParam(value = "sortField", required = false, defaultValue = "idAgenda") String sortField,
			@RequestParam(value = "sortOrder", required = false, defaultValue = "0") String sortOrder,
			@RequestParam(value = "orcamentoNumber", required = false, defaultValue = "") String orcamentoNumber,
			@RequestParam(value = "valorMin", required = false, defaultValue = "") String valorMin,
			@RequestParam(value = "valorMax", required = false, defaultValue = "") String valorMax) {

		return ResponseEntity.ok(service.filterByCriteria(first, rows, nomes, idAgenda, estados, tipoCliente, cliente,
				visitaDe, visitaAte, retornoDe, retornoAte, observacao, orcamentoNumber, valorMin, valorMax, sortField,
				sortOrder));
	}

	@PutMapping(value = "/{idAgenda}")
	public ResponseEntity<?> atualizar(@Valid @RequestBody Agenda agenda, @PathVariable Long idAgenda,
			BindingResult result) {

		verificarStatus(agenda.getStatus(), idAgenda, result);

		if (result.hasErrors()) {
			List<String> erros = new ArrayList<>();
			result.getAllErrors().forEach(err -> {
				erros.add(err.getDefaultMessage());
			});
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(erros);
		}

		service.update(agenda, idAgenda);
		return ResponseEntity.accepted().build();
	}

	@PreAuthorize("hasAnyRole('GESTOR')")
	@DeleteMapping(value = "/{idAgenda}/historico/{idGenderDate}")
	public ResponseEntity<?> deleteGenderDateById(@PathVariable Long idGenderDate) {
		System.out.println(idGenderDate);
		genderDateService.deleteById(idGenderDate);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('GESTOR')")
	@DeleteMapping(value = "/{idAgenda}")
	public ResponseEntity<?> deleteGender(@PathVariable Long idAgenda) {
		System.out.println(idAgenda);
		service.delete(idAgenda);
		return ResponseEntity.noContent().build();
	}
	
	private void verificarStatus(StatusGender stGender, Long idAgenda, BindingResult result) {
		if (genderDateService.findByStatusAndIdAgenda(stGender, idAgenda).isPresent()
				&& stGender != StatusGender.EM_ANDAMENTO) {
			result.addError(new ObjectError("Status", "Status já utilizado"));
		}
	}

	@GetMapping(value = "/cliente")
	public ResponseEntity<?> getAllClientes(
			@RequestParam(value = "cliente", required = true, defaultValue = "") String cliente) {
		System.out.println(cliente);
		return ResponseEntity.ok(service.findCliente(cliente));
	}

	@GetMapping(value = "/contato")
	public ResponseEntity<?> getAllContato(
			@RequestParam(value = "contato", required = true, defaultValue = "") String contato) {
		return ResponseEntity.ok(service.findContato(contato));
	}

}
