package com.edfer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.logistica.FornecedorItensServicosVeiculos;
import com.edfer.repository.logistica.FornecedorItensServicosVeiculosRepository;
import com.edfer.service.FornecedorItensServicosVeiculosService;

@Service
@Transactional
public class FornecedorItensServicosVeiculosImpl implements FornecedorItensServicosVeiculosService {

	@Autowired
	private FornecedorItensServicosVeiculosRepository repository;

	@Override
	public void salvar(FornecedorItensServicosVeiculos fornecedor) {
		repository.save(fornecedor);
	}

	@Override
	public void update(FornecedorItensServicosVeiculos fornecedor, Long idFornecedor) {
		fornecedor.setIdFornecedor(idFornecedor);
		repository.save(fornecedor);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FornecedorItensServicosVeiculos> findAll() {
		return repository.findAll();
	}

	@Override
	public void remover(Long idFornecedor) {
		repository.deleteById(idFornecedor);
	}

	@Override
	@Transactional(readOnly = true)
	public FornecedorItensServicosVeiculos findById(Long idFornecedor) {
		return repository.findById(idFornecedor).get();
	}
}
