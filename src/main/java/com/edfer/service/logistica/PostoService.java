package com.edfer.service.logistica;

import java.util.List;
import java.util.Optional;

import com.edfer.model.logistica.Posto;

public interface PostoService {

	void salvar(Posto posto);
	
	void update(Long idPosto, Posto posto);
	
	void delete(Long idPosto);
	
	Optional<Posto> findById(Long idPosto);
	
	Optional<Posto> findByNomeIgnoreCase(String nome);
	
	List<Posto> findAll();
}
