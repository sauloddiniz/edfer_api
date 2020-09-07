package com.edfer.service;

import java.util.List;

import com.edfer.model.Orcamento;

public interface OrcamentoService {

	void salvar(Orcamento orcamento);

	void update(Long idOrcamento, Orcamento orcamento);

	void delete(Long idOrcamento);
	
	Orcamento findById(Long idOrcamento);
	
	List<Orcamento> findAll();
}
