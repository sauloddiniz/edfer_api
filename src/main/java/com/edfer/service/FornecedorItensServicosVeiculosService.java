package com.edfer.service;

import java.util.List;

import com.edfer.model.logistica.FornecedorItensServicosVeiculos;

public interface FornecedorItensServicosVeiculosService {

	void salvar(FornecedorItensServicosVeiculos fornecedor);

	void update(FornecedorItensServicosVeiculos fornecedor, Long idFornecedor);

	List<FornecedorItensServicosVeiculos> findAll();

	void remover(Long idFornecedor);

	FornecedorItensServicosVeiculos findById(Long idFornecedor);
}
