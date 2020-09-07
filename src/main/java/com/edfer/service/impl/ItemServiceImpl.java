package com.edfer.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edfer.model.Item;
import com.edfer.repository.ItemRepository;
import com.edfer.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemRepository repository;

	@Override
	public void salvar(Item item) {
		repository.save(item);
	}

	@Override
	public void delete(Long idItem) {
		repository.deleteById(idItem);
	}

	@Override
	public void update(Long idItem, Item item) {
		item.setIdItem(idItem);
		repository.save(item);
	};

	@Override
	public Optional<Item> findById(Long idItem) {
		return repository.findById(idItem);
	}

	@Override
	public Optional<Item> findByNome(String nome) {
		return repository.findByNomeIgnoreCase(nome);
	}

	@Override
	public List<Item> findAll() {
		return repository.findAll();
	}

}
