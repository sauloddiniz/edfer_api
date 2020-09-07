package com.edfer.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edfer.model.Categoria;
import com.edfer.repository.CategoriaRepository;
import com.edfer.service.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	@Override
	public void salvar(Categoria categoria) {
		repository.save(categoria);
	}

	@Override
	public void delete(Long idCategoria) {
		repository.deleteById(idCategoria);
	}

	@Override
	public void update(Long idCategoria, Categoria categoria) {
		categoria.setIdCategoria(idCategoria);
		repository.save(categoria);
	}

	@Override
	public Optional<Categoria> findById(Long idCategoria) {
		return repository.findById(idCategoria);
	}

	@Override
	public Optional<Categoria> findByNome(String nome) {
		return repository.findByNomeIgnoreCase(nome);
	}

	@Override
	public List<Categoria> findAll() {
		return repository.findAll();
	}

}
