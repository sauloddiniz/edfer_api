package com.edfer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edfer.model.OrcamentoItem;
import com.edfer.repository.OrcamentoItemRepository;
import com.edfer.service.OrcamentoItemService;

@Service
public class OrcamentoItemServiceImpl implements OrcamentoItemService {

	@Autowired
	private OrcamentoItemRepository repository;

	@Override
	public List<OrcamentoItem> findAll() {
		return repository.findAll();
	}

}
