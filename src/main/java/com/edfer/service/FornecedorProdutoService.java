package com.edfer.service;

import java.util.List;
import java.util.Optional;

import com.edfer.model.FornecedorProduto;

public interface FornecedorProdutoService {
	
	void salvar(FornecedorProduto fornecedorProduto);

	void update(FornecedorProduto fornecedorProduto);
	
	void delete(Long idFornecedorProduto);
	
	Optional<FornecedorProduto> findById(Long idFornecedorProduto);
	
	Optional<FornecedorProduto> findByNome(String nome);

	List<FornecedorProduto> findAll();
	
	List<FornecedorProduto> findByNomeStartingWith(String nome);
}
