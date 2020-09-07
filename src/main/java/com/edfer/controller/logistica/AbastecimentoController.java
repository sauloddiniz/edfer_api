package com.edfer.controller.logistica;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.edfer.model.logistica.Abastecimento;
import com.edfer.service.logistica.AbastecimentoService;
import com.edfer.service.logistica.VeiculoService;

@RestController
@RequestMapping(value = "abastecimento")
@CrossOrigin(value = "*")
@PreAuthorize("hasAnyRole('LOGISTICA')")
public class AbastecimentoController {

	@Autowired
	private AbastecimentoService service;
	
	@Autowired
	private VeiculoService veiculoService;

	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody Abastecimento abastecimento, BindingResult bindingResult) {

		findPrevios(abastecimento, bindingResult);

		if (bindingResult.hasErrors()) {
			List<String> erros = new ArrayList<>();
			bindingResult.getAllErrors().forEach(err -> {
				erros.add(err.getDefaultMessage());
			});
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(erros);
		}

		service.salvar(abastecimento);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/id")
				.buildAndExpand(abastecimento.getIdAbastecimento()).toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping(value = "/{idVeiculo}")
	public ResponseEntity<?> getAllByIdVeiculo(@PathVariable(value = "idVeiculo") Long idVeiculo) {
		return ResponseEntity.ok(service.findPrevios30ByVeiculo(idVeiculo));
	}

	@GetMapping(value = "/one/{idAbastecimento}")
	public ResponseEntity<?> getAbastecimentoByid(@PathVariable(value = "idAbastecimento") Long idAbastecimento) {
		return ResponseEntity.ok(service.findById(idAbastecimento));
	}
	
	@PutMapping(value="/{idAbastecimento}")
	public ResponseEntity<?> update(
			@PathVariable(value="idAbastecimento")Long idAbastecimento,
			@Valid @RequestBody Abastecimento abastecimento, BindingResult bindingResult) {

		service.update(idAbastecimento, abastecimento);
		
		return ResponseEntity.accepted().build();
	}

	@DeleteMapping(value="/{idAbastecimento}")
	public ResponseEntity<?> removeById(
			@PathVariable(value="idAbastecimento")Long idAbastecimento) {
		System.out.println(idAbastecimento);
		service.delete(idAbastecimento);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/list")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@PutMapping(value="/consumoMedio/{idAbastecimento}")
	public ResponseEntity<?> updateCo1nsumoMedio(
			@PathVariable Long idAbastecimento,
			@Valid @RequestBody Abastecimento abastecimento, BindingResult bindingResult) {
		
		Optional<Long> previous = service.findByHodometroLess(abastecimento.getVeiculo().getIdVeiculo(), abastecimento.getHodometro());
	
		if(previous.isPresent()) {
			service.salvar(abastecimento);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = "/criteria")
	public ResponseEntity<?> listByCriteria(
			@RequestParam(name = "rows", defaultValue = "", required = false) String rows,
			@RequestParam(name = "first", defaultValue = "", required = false) String first,
			@RequestParam(name = "sortField", defaultValue = "dataAbastecimento", required = false) String sortField,
			@RequestParam(name = "sortOrder", defaultValue = "0", required = false) String sortOrder,
			@RequestParam(name = "idAbastecimento", defaultValue = "", required = false) String idAbastecimento,
			@RequestParam(name = "veiculos", defaultValue = "", required = false) String veiculos,
			@RequestParam(name = "abastecimentoDateMin", defaultValue = "", required = false) String abastecimentoDateMin,
			@RequestParam(name = "abastecimentoDateMax", defaultValue = "", required = false) String abastecimentoDateMax,
			@RequestParam(name = "postos", defaultValue = "", required = false) String postos,
			@RequestParam(name = "tipoCombustivel", defaultValue = "", required = false) String tipoCombustivel) {

		return ResponseEntity.ok(service.findByCriteria(rows, first, sortField, sortOrder, idAbastecimento, veiculos, abastecimentoDateMin, abastecimentoDateMax, postos, tipoCombustivel));
	}

	private void findPrevios(Abastecimento abastecimento, BindingResult bindingResult) {		
		Optional<Abastecimento> opPrevius = service.findPreviusAbastecimentoByDate(abastecimento.getVeiculo().getIdVeiculo(), abastecimento.getDataAbastecimento());

		if (opPrevius.isPresent()) {
			if (abastecimento.getHodometro() <= opPrevius.get().getHodometro()) {
				bindingResult.addError(new ObjectError("Error", "A Km não pode ser menor que a anterior: " + opPrevius.get().getHodometro() ));
			} else {
				opPrevius.get().setConsumoMedio(
						(abastecimento.getHodometro() - opPrevius.get().getHodometro()) / abastecimento.getQuantidadeLitro());
				veiculoService.updateHodometroViaAbastecimento(abastecimento.getVeiculo().getIdVeiculo(), abastecimento.getHodometro());
				service.salvar(opPrevius.get());
			}
		}
	}

}
