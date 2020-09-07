package com.edfer.service;

import java.util.List;
import java.util.Optional;

import com.edfer.model.Produto;
import com.edfer.model.filtersDTO.FilterDTO;

public interface ProdutoService {

	void salvar(Produto produto);

	void atualizar(Produto produto);

	void delete(Long idProduto);

	Optional<Produto> findByIdProduto(Long idProduto);
	
	Optional<Produto> findByDescricao(String descricao);
	
	Optional<Produto> findByCodigo(String codigo);
	
	List<Produto> findAll();

	List<Produto> findByCodigoStartingWith(String codigo);
	
	List<Produto> findByDescricaoContains(String descricao);
	
	FilterDTO findAllFilterCriteria(String first, String rows, String codigo, String descricao, String sortField,
			String sortOrder);
}
