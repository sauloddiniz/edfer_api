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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edfer.model.Produto;
import com.edfer.service.ProdutoService;

@RestController
@RequestMapping(value = "produto")
@CrossOrigin(value = "*")
public class ProdutoController {

	@Autowired
	private ProdutoService service;

	@GetMapping
	public ResponseEntity<?> getProdutoByCodigo(
			@RequestParam(value = "tipo", required = false, defaultValue = "one") String tipo,
			@RequestParam(value = "codigo", required = false) String codigo) {
		return tipo.equals("one") ? ResponseEntity.ok(service.findByCodigo(codigo))
				: ResponseEntity.ok(service.findByCodigoStartingWith(codigo));
	}

	@GetMapping(value = "/lista")
	public ResponseEntity<?> getAllProduto(
			@RequestParam(value = "contains", required = false, defaultValue = "") String contains) {
		return contains.equals("") ? ResponseEntity.ok(service.findAll())
				: ResponseEntity.ok(service.findByDescricaoContains(contains));
	}

	@GetMapping(value = "/lista/filters")
	public ResponseEntity<?> getAllProdutoFilter(
			@RequestParam(value = "rows", required = false, defaultValue = "") String rows,
			@RequestParam(value = "first", required = false, defaultValue = "") String first,
			@RequestParam(value = "codigo", required = false, defaultValue = "") String codigo,
			@RequestParam(value = "descricao", required = false, defaultValue = "") String descricao,
			@RequestParam(value = "sortField", required = false, defaultValue = "") String sortField,
			@RequestParam(value = "sortOrder", required = false, defaultValue = "1") String sortOrder) {
		
		return ResponseEntity.ok(
				service.findAllFilterCriteria(first, rows, codigo, descricao, sortField, sortOrder));
	}

	@PostMapping
	public ResponseEntity<?> salvarProduto(@Valid @RequestBody Produto produto, BindingResult result) {

		validProduto(produto, result);
		if (result.hasErrors()) {
			List<String> error = new ArrayList<>();
			result.getAllErrors().forEach(err -> error.add(err.getDefaultMessage()));
			System.out.println(result);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
		}

		service.salvar(produto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + produto.getIdProduto().toString())
				.buildAndExpand().toUri();

		return ResponseEntity.created(uri).build();
	}

	@GetMapping(value = "/{idProduto}")
	public ResponseEntity<?> findByIdFornecedor(@PathVariable("idProduto") Long idProduto) {
		return ResponseEntity.ok(service.findByIdProduto(idProduto));
	}

	@DeleteMapping(value = "/{idFornecedorProduto}")
	public ResponseEntity<?> deleteFornecedorById(@PathVariable("idFornecedorProduto") Long idFornecedorProduto) {
		service.delete(idFornecedorProduto);
		return ResponseEntity.noContent().build();
	}

	private void validProduto(Produto produto, BindingResult result) {
		service.findByDescricao(produto.getDescricao()).ifPresent(obj -> {
			if (produto.getIdProduto() != obj.getIdProduto()) {
				result.addError(new ObjectError("Error", "Descricão já cadastrada"));
			}
		});

		service.findByCodigo(produto.getCodigo()).ifPresent(obj -> {
			if (produto.getIdProduto() != obj.getIdProduto()) {
				result.addError(new ObjectError("Error", "Código já cadastrada"));
			}
		});
	}

}
