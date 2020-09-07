package com.edfer.service;

import java.util.List;
import java.util.Optional;

import com.edfer.model.Item;

public interface ItemService {

	void salvar(Item item);

	void delete(Long idItem);

	void update(Long idItem, Item item);

	Optional<Item> findById(Long idItem);

	Optional<Item> findByNome(String nome);

	List<Item> findAll();

}
