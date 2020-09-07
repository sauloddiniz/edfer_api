package com.edfer.service.logistica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edfer.model.logistica.Motorista;
import com.edfer.repository.logistica.MotoristaRepository;

@Service
public class MotoristaServiceImpl implements MotoristaService{

	@Autowired
	private MotoristaRepository repository;
	
	@Override
	public void salvar(Motorista motorista) {
		repository.save(motorista);
	}

	@Override
	public List<Motorista> findAllIsAtivo() {
		return repository.findAllByIsAtivoTrue();
	}

}
