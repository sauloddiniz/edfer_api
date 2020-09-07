package com.edfer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.Orcamento;
import com.edfer.model.OrcamentoItem;
import com.edfer.model.OrcamentoItemFornecedor;
import com.edfer.repository.OrcamentoRepository;
import com.edfer.service.OrcamentoService;

@Service
@Transactional
public class OrcamentoServiceImpl implements OrcamentoService{

	@Autowired
	private OrcamentoRepository repository;
	
	@Override
	public void salvar(Orcamento orcamento) {
		repository.save(orcamento);
		
		if (orcamento.getListaItemOrc() != null) {
			for (OrcamentoItem item : orcamento.getListaItemOrc()) {
				orcamento.addOrcamentoItem(item);
				for (OrcamentoItemFornecedor fornecedor : item.getFornecedores()) {
					item.addFornecedor(fornecedor);
					fornecedor.addFormaPagamento(fornecedor.getFormaPagamento());
				}
			}
		}
	}

	@Override
	public void update(Long idOrcamento, Orcamento orcamento) {
		orcamento.setIdOrcamento(idOrcamento);
		repository.save(orcamento);
	}

	@Override
	public void delete(Long idOrcamento) {
		repository.deleteById(idOrcamento);
	}

	@Override
	@Transactional(readOnly = true)
	public Orcamento findById(Long idOrcamento) {
		return repository.findById(idOrcamento).get();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Orcamento> findAll() {
		return repository.findAll();
	}

}
