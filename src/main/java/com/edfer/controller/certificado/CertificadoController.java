package com.edfer.controller.certificado;

import java.net.URI;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edfer.model.certificado.Certificado;
import com.edfer.model.certificado.ClienteCertificado;
import com.edfer.model.certificado.ProdutoCertificado;
import com.edfer.model.filtersDTO.FieldToJsonDTO;
import com.edfer.model.filtersDTO.FilterDTO;
import com.edfer.service.certificado.CertificadoService;
import com.edfer.service.certificado.ClienteCertificadoService;
import com.edfer.service.certificado.ProdutoCertificadoService;

@RequestMapping(value = "certificados")
@RestController
@CrossOrigin(origins = "*")
@PreAuthorize("hasAnyRole('VENDA')")
public class CertificadoController {

	@Autowired
	private ProdutoCertificadoService produtoService;

	@Autowired
	private CertificadoService certificadoService;

	@Autowired
	private ClienteCertificadoService clienteService;

	@PostMapping(value = "produto")
	public ResponseEntity<?> save(@Valid @RequestBody ProdutoCertificado produtoCertificado,
			BindingResult bindingResult) {
		
		Optional<ProdutoCertificado> aux = produtoService.findByCodigo(produtoCertificado.getCodigo());
		if (aux.isPresent()) {
			bindingResult.addError(new FieldError("ProdutoCertificado", "codigo", "Código já cadastrado"));
		}

		if (bindingResult.hasErrors()) {
			List<FieldToJsonDTO> erros = bindingResult.getFieldErrors().parallelStream()
					.map(err -> new FieldToJsonDTO(err.getField(), err.getDefaultMessage()))
					.collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(erros);
		}

		produtoService.save(produtoCertificado);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/" + produtoCertificado.getIdProdutoCertificado().toString()).buildAndExpand().toUri();

		return ResponseEntity.created(uri).build();
	}

	@GetMapping(value = "produto/{codigo}")
	public ResponseEntity<?> findByStartingWith(@PathVariable(value = "codigo") Long codigo) {
		List<?> lista = produtoService.startingWithCodigo(codigo);
		return !lista.isEmpty() ? ResponseEntity.ok(lista) : ResponseEntity.notFound().build();
	}

	@GetMapping(value = "fornecedor/{fornecedor}")
	public ResponseEntity<?> findByFornecedor(@PathVariable(value = "fornecedor") String fornecedor) {
		List<?> lista = certificadoService.findFornecedores(fornecedor);
		return !lista.isEmpty() ? ResponseEntity.ok(lista) : ResponseEntity.notFound().build();
	}

	@GetMapping(value = "norma/{norma}")
	public ResponseEntity<?> findAllNorma(@PathVariable(value = "norma") String norma) {
		List<?> list = certificadoService.startingWithNorma(norma);
		return !list.isEmpty() ? ResponseEntity.ok(list) : ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> saveCertificados(@Valid @RequestBody Certificado certificado,
			BindingResult bindingResult) {
		
		Optional<String> aux = certificadoService.findByNumero(certificado.getNumero());
		if (aux.isPresent()) {
			bindingResult.addError(new FieldError("Certificado", "numero", "Nº já cadastrado"));
		}

		if (bindingResult.hasErrors()) {
			List<FieldToJsonDTO> erros = bindingResult.getFieldErrors().parallelStream()
					.map(err -> new FieldToJsonDTO(err.getField(), err.getDefaultMessage()))
					.collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(erros);
		}

		certificadoService.save(certificado);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + certificado.getIdCertificado().toString())
				.buildAndExpand().toUri();

		return ResponseEntity.created(uri).build();
	}

	@GetMapping(value = "produto-criteria")
	public ResponseEntity<?> findProdutoByCriteria(
			@RequestParam(value = "rows", required = false, defaultValue = "") String rows,
			@RequestParam(value = "first", required = false, defaultValue = "") String first,
			@RequestParam(value = "sortField", required = false, defaultValue = "codigo") String sortField,
			@RequestParam(value = "sortOrder", required = false, defaultValue = "1") String sortOrder,
			@RequestParam(value = "codigo", required = false, defaultValue = "") String codigo,
			@RequestParam(value = "classe", required = false, defaultValue = "") String classe,
			@RequestParam(value = "descricao", required = false, defaultValue = "") String descricao) {

		Optional<FilterDTO> optional = Optional
				.of(produtoService.findProdutoByCriteria(first, rows, sortField, sortOrder, codigo, classe, descricao));

		return optional.isPresent() ? ResponseEntity.ok(optional.get()) : ResponseEntity.notFound().build();
	}

	@GetMapping(value = "certificado-criteria")
	public ResponseEntity<?> findCertificadoByCriteria(
			@RequestParam(value = "rows", required = false, defaultValue = "") String rows,
			@RequestParam(value = "first", required = false, defaultValue = "") String first,
			@RequestParam(value = "sortField", required = false, defaultValue = "dataEmissao") String sortField,
			@RequestParam(value = "sortOrder", required = false, defaultValue = "0") String sortOrder,
			@RequestParam(value = "codigo", required = false, defaultValue = "") String codigo,
			@RequestParam(value = "dateMin", required = false, defaultValue = "") String dateMin,
			@RequestParam(value = "dateMax", required = false, defaultValue = "") String dateMax,
			@RequestParam(value = "numero", required = false, defaultValue = "") String numero,
			@RequestParam(value = "fornecedor", required = false, defaultValue = "") String fornecedor,
			@RequestParam(value = "norma", required = false, defaultValue = "") String norma,
			@RequestParam(value = "corrida", required = false, defaultValue = "") String corrida,
			@RequestParam(value = "volume", required = false, defaultValue = "") String volume,
			@RequestParam(value = "dimensao", required = false, defaultValue = "") String dimensao) {
	
		Optional<FilterDTO> optional = Optional.of(certificadoService.findCertificadoByCriteria(first, rows, sortField,
				sortOrder, codigo, dateMin, dateMax, numero, fornecedor, norma, corrida, volume, dimensao));

		return optional.isPresent() ? ResponseEntity.ok(optional.get()) : ResponseEntity.notFound().build();
	}

	@PostMapping(value = "/{idCertificado}/cliente")
	public ResponseEntity<?> salvarCliente(@Valid @RequestBody ClienteCertificado cliente,
			@PathVariable(value = "idCertificado") Long idCertificado, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			List<String> erros = new ArrayList<>();
			bindingResult.getAllErrors().forEach(err -> erros.add(err.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(erros);
		}

		clienteService.salvar(cliente, idCertificado);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/id")
				.buildAndExpand(cliente.getIdClienteCertificado()).toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping(value = "/{idProduto}")
	public ResponseEntity<?> findAllByProduto(@PathVariable("idProduto") Long idProduto) {
		List<?> list = certificadoService.findAllByIdProduto(idProduto);
		return !list.isEmpty() ? ResponseEntity.ok(list) : ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/{numPedido}/pedido")
	public ResponseEntity<?> findAllClienteByNumPedido(@PathVariable("numPedido") String numPedido) {
		List<?> list = clienteService.findAllByNumPedido(numPedido);
		return !list.isEmpty() ? ResponseEntity.ok(list) : ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/{idCertificado}/cliente")
	public ResponseEntity<?> findAllClientesByIdCertificado(@PathVariable("idCertificado") Long idCertificado) {
		List<?> list = clienteService.findAllByIdCertificado(idCertificado);
		return !list.isEmpty() ? ResponseEntity.ok(list) : ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/{idCertificado}")
	public ResponseEntity<?> findCertificadoById(@PathVariable("idCertificado") Long idCertificado) {
		Optional<Certificado> cerOptional = certificadoService.findByIdCertificado(idCertificado);
		return cerOptional.isPresent() ? ResponseEntity.ok(cerOptional.get()) : ResponseEntity.notFound().build();
	}
	
	@PutMapping
	public ResponseEntity<?> updateCertificado(@Valid @RequestBody Certificado certificado, BindingResult bindingResult) {

		verify(certificado, bindingResult);

		if (bindingResult.hasErrors()) {
			List<FieldToJsonDTO> erros = bindingResult.getFieldErrors().parallelStream()
					.map(err -> new FieldToJsonDTO(err.getField(), err.getDefaultMessage()))
					.collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(erros);
		}

		certificadoService.save(certificado);

		return ResponseEntity.noContent().build();
	}

	private void verify(Certificado certificado, BindingResult bindingResult) {
		Optional<Certificado> aux = certificadoService.findByIdCertificado(certificado.getIdCertificado());

		if (!(aux.get().getNumero().equals(certificado.getNumero()))) {
			if(certificadoService.findByNumero(certificado.getNumero()).isPresent()) {
				bindingResult.addError(new FieldError("Certificado", "numero", "Nº já cadastrado"));				
			}
		}
	}
}
