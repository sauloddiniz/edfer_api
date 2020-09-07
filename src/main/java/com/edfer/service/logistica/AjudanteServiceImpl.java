package com.edfer.service.logistica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edfer.model.logistica.Ajudante;
import com.edfer.repository.logistica.AjudanteRepository;

@Service
public class AjudanteServiceImpl implements AjudanteService{

	@Autowired
	private AjudanteRepository repository;
	
	@Override
	public void salvar(Ajudante ajudante) {
		repository.save(ajudante);
	}

	@Override
	public List<Ajudante> findAllisAtivo() {
		return repository.findAllByIsAtivoTrue();
	}

}
