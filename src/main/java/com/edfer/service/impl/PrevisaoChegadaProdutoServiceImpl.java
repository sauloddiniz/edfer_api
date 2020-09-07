package com.edfer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.PrevisaoChegadaProduto;
import com.edfer.repository.PrevisaoChegadaProdutoRepository;
import com.edfer.service.PrevisaoChegadaProdutoService;

@Service
@Transactional
public class PrevisaoChegadaProdutoServiceImpl implements PrevisaoChegadaProdutoService{

	@Autowired
	private PrevisaoChegadaProdutoRepository repository;

	@Override
	public void salvar(PrevisaoChegadaProduto previsaoChegadaProduto) {

		repository.save(previsaoChegadaProduto);

		if(previsaoChegadaProduto.getProdutosChegada() != null) {
			previsaoChegadaProduto.getProdutosChegada()
			.parallelStream()
			.forEach(previsaoChegadaProduto::addProdutoChegada);			
		}
	}

	@Override
	public void alterar(PrevisaoChegadaProduto previsaoChegadaProduto, Long idPrevisaoChagadaProduto) {
		repository.save(previsaoChegadaProduto);
	}

	@Override
	public void delete(Long idPrevisaoChagadaProduto) {
		repository.deleteById(idPrevisaoChagadaProduto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PrevisaoChegadaProduto> findAll() {
		return repository.findAll();
	}

}
