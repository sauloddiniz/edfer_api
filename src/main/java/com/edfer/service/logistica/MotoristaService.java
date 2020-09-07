package com.edfer.service.logistica;

import java.util.List;

import org.springframework.stereotype.Service;

import com.edfer.model.logistica.Motorista;

@Service
public interface MotoristaService {

	public void salvar(Motorista motorista);

	List<Motorista> findAllIsAtivo();
}
