package com.edfer.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

import com.edfer.model.EntradaPedido;
import com.edfer.model.Pedido;
import com.edfer.model.PedidoSeparacao;
import com.edfer.model.enuns.EnumLocalizacaoPedido;
import com.edfer.model.enuns.EnumStatusPedido;
import com.edfer.service.EntradaPedidoService;
import com.edfer.service.PedidoSeparacaoService;
import com.edfer.service.PedidoService;

@RequestMapping(value = "pedidos")
@RestController
@CrossOrigin(value = "*")
public class PedidoController {

	@Autowired
	private PedidoService service;

	@Autowired
	private EntradaPedidoService serviceEntrada;

	@Autowired
	private PedidoSeparacaoService serviceSeparacao;
	
	@PostMapping
	public ResponseEntity<?> salvarPedido(@Valid @RequestBody Pedido pedido, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			List<String> erros = new ArrayList<>();
			bindingResult.getAllErrors().forEach(err -> {
				erros.add(err.getDefaultMessage());
			});
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(erros);
		}
		service.salvar(pedido);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(pedido.getIdPedido())
				.toUri();

		return ResponseEntity.created(location).body("");
	}

	@PutMapping(value = "/{idPedido}")
	public ResponseEntity<?> updatePedido(@PathVariable(name = "idPedido") Long idPedido,
			@Valid @RequestBody Pedido pedido) {
		System.out.println(idPedido);
		System.out.println(pedido.toString());
		service.update(idPedido, pedido);
		return ResponseEntity.noContent().build();
	}

	@PostMapping(value = "/entrada")
	public ResponseEntity<?> salvarEntrada(@Valid @RequestBody EntradaPedido entradaPedido,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			List<String> erros = new ArrayList<>();
			bindingResult.getAllErrors().forEach(err -> {
				erros.add(err.getDefaultMessage());
			});
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(erros);
		}
		serviceEntrada.salvar(entradaPedido);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/id")
				.buildAndExpand(entradaPedido.getIdEntradaPedido()).toUri();

		return ResponseEntity.created(location).body(entradaPedido.getIdEntradaPedido());
	}

	@GetMapping(value = "/entrada/criteria")
	public ResponseEntity<?> buscarEntradas(
			@RequestParam(value = "sortField", required = false, defaultValue = "idEntradaPedido") String sortField,
			@RequestParam(value = "sortOrder", required = false, defaultValue = "0") String sortOrder,
			@RequestParam(value = "rows", required = false, defaultValue = "") String rows,
			@RequestParam(value = "first", required = false, defaultValue = "") String first,
			@RequestParam(value = "numeroPedido", required = false, defaultValue = "") String numeroPedido,
			@RequestParam(value = "cliente", required = false, defaultValue = "") String cliente,
			@RequestParam(value = "rota", required = false, defaultValue = "") String rota,
			@RequestParam(value = "localizacao", required = false, defaultValue = "") String localizacao,
			@RequestParam(value = "pesoInicio", required = false, defaultValue = "") String pesoInicio,
			@RequestParam(value = "pesoFinal", required = false, defaultValue = "") String pesoFinal,
			@RequestParam(value = "previsaoInicio", required = false, defaultValue = "") String previsaoInicio,
			@RequestParam(value = "previsaoFinal", required = false, defaultValue = "") String previsaoFinal,
			@RequestParam(value = "status", required = false, defaultValue = "") String status,
			@RequestParam(value = "localizacaoPedido", required = false, defaultValue = "") String localizacaoPedido,
			@RequestParam(value = "situacaoFinal", required = false, defaultValue = "") String situacaoFinal,
			@RequestParam(value = "dataCriacaoInicio", required = false, defaultValue = "") String dataCriacaoInicio,
			@RequestParam(value = "dataCriacaoFinal", required = false, defaultValue = "") String dataCriacaoFinal) {

		return ResponseEntity.ok(serviceEntrada.findAllByCriteria(sortField, sortOrder, rows, first, numeroPedido,
				cliente, rota, localizacao, pesoInicio, pesoFinal, previsaoInicio, previsaoFinal, status,
				localizacaoPedido, situacaoFinal, dataCriacaoInicio, dataCriacaoFinal));
	}

	@GetMapping(value = "/rotas")
	public ResponseEntity<?> getAllRotas(
			@RequestParam(value = "rota", required = true, defaultValue = "") String rota) {
		return ResponseEntity.ok(service.getAllRotas(rota));
	}

	@GetMapping(value = "/localizacao")
	public ResponseEntity<?> getAllLocalizacao(
			@RequestParam(value = "localizacao", required = true, defaultValue = "") String localizacao) {
		return ResponseEntity.ok(service.getAllLocalizacao(localizacao));
	}

	@GetMapping(value = "/isnull")
	public ResponseEntity<?> findPedidoIsNull() {
		service.countByPatios(EnumLocalizacaoPedido.TELHAS, EnumStatusPedido.EM_ANDAMENTO);
		return ResponseEntity.ok(serviceEntrada.countByPedidoIsNull());
	}

	@GetMapping(value = "/list")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@PostMapping(value ="/pedidoSeparacao")
	public ResponseEntity<?> salvarPedidoSeparacao(@RequestBody PedidoSeparacao pedidoSeparacao) {
		System.out.println(pedidoSeparacao.toString());
		serviceSeparacao.salvar(pedidoSeparacao);
		return ResponseEntity.ok().build();
	}
}
