package com.edfer.service;

import java.util.List;
import java.util.Optional;

import com.edfer.model.Categoria;

public interface CategoriaService {
	
	void salvar(Categoria categoria);

	void delete(Long idCategoria);

	void update(Long idCategoria, Categoria categoria);

	Optional<Categoria> findById(Long idCategoria);

	Optional<Categoria> findByNome(String nome);

	List<Categoria> findAll();
}
