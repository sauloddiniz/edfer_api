package com.edfer.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.Produto;
import com.edfer.model.filtersDTO.FilterDTO;
import com.edfer.repository.ProdutoRepository;
import com.edfer.service.ProdutoService;

@Service
@Transactional
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoRepository repository;
	
	@Override
	public void salvar(Produto produto) {
		repository.save(produto);
	}

	@Override
	public void atualizar(Produto produto) {
		repository.save(produto);
	}

	@Override
	public void delete(Long idProduto) {
		repository.deleteById(idProduto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Produto> findByIdProduto(Long idProduto) {
		return repository.findById(idProduto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Produto> findByDescricao(String descricao) {
		return repository.findByDescricao(descricao);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Produto> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Produto> findByCodigo(String codigo) {
		return repository.findByCodigo(codigo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Produto> findByCodigoStartingWith(String codigo) {
		return repository.findByCodigoStartingWith(codigo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Produto> findByDescricaoContains(String descricao) {
		return repository.findByDescricaoContaining(descricao);
	}

	@Override
	@Transactional(readOnly = true)
	public FilterDTO findAllFilterCriteria(String first, String rows, String codigo, String descricao, String sortField, String sortOrder) {
		return repository.findAllFilterCriteria(Integer.parseInt(first), Integer.parseInt(rows), codigo, descricao, sortField, sortOrder);
	}

}
