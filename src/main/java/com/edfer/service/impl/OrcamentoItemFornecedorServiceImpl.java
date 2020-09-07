package com.edfer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edfer.model.OrcamentoItemFornecedor;
import com.edfer.repository.OrcamentoItemFornecedorRepository;
import com.edfer.service.OrcamentoItemFornecedorService;

@Service
public class OrcamentoItemFornecedorServiceImpl implements OrcamentoItemFornecedorService {

	@Autowired
	private OrcamentoItemFornecedorRepository repository;

	@Override
	public List<OrcamentoItemFornecedor> findAll() {
		return repository.findAll();
	}

}
