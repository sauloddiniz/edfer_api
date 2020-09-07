package com.edfer.service;

import java.util.List;

import com.edfer.model.PrevisaoChegadaProduto;

public interface PrevisaoChegadaProdutoService {

	void salvar(PrevisaoChegadaProduto previsaoChegadaProduto);
	
	void alterar(PrevisaoChegadaProduto chegadaProduto, Long idPrevisaoChagadaProduto);
	
	void delete(Long idPrevisaoChagadaProduto);
	
	List<PrevisaoChegadaProduto> findAll();
}
