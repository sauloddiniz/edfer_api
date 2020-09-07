package com.edfer.service.logistica;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edfer.model.logistica.Posto;
import com.edfer.repository.logistica.PostoRepository;

@Service
public class PostoServiceImpl implements PostoService{

	@Autowired
	private PostoRepository repository;
	
	@Override
	public void salvar(Posto posto) {
		repository.save(posto);
	}

	@Override
	public void update(Long idPosto, Posto posto) {
		posto.setIdPosto(idPosto);
		repository.save(posto);
	}

	@Override
	public void delete(Long idPosto) {
		repository.deleteById(idPosto);
	}

	@Override
	public Optional<Posto> findById(Long idPosto) {
		return repository.findById(idPosto);
	}

	@Override
	public Optional<Posto> findByNomeIgnoreCase(String nome) {
		return repository.findByNomeIgnoreCase(nome);
	}

	@Override
	public List<Posto> findAll() {
		return repository.findAll();
	}

}
