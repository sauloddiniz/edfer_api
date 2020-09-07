package com.edfer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edfer.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{
	
	Optional<Item> findByNomeIgnoreCase(String nome);

}
