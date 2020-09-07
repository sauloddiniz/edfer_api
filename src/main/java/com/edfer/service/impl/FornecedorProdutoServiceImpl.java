package com.edfer.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.FornecedorProduto;
import com.edfer.repository.FornecedorProdutoRepository;
import com.edfer.service.FornecedorProdutoService;

@Service
@Transactional
public class FornecedorProdutoServiceImpl implements FornecedorProdutoService {

	@Autowired
	private FornecedorProdutoRepository repository;
	
	@Override
	public void salvar(FornecedorProduto fornecedorProduto) {
		repository.save(fornecedorProduto);
	}

	@Override
	public void update(FornecedorProduto fornecedorProduto) {
		repository.save(fornecedorProduto);
	}

	@Override
	public void delete(Long idFornecedorProduto) {
		repository.deleteById(idFornecedorProduto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<FornecedorProduto> findById(Long idFornecedorProduto) {
		return repository.findById(idFornecedorProduto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FornecedorProduto> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<FornecedorProduto> findByNome(String nome) {
		return repository.findByNome(nome);
	}

	@Override
	@Transactional
	public List<FornecedorProduto> findByNomeStartingWith(String nome) {
		return repository.findByNomeStartingWith(nome);
	}

}
