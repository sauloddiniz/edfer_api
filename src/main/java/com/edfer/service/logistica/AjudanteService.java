package com.edfer.service.logistica;

import java.util.List;

import com.edfer.model.logistica.Ajudante;

public interface AjudanteService {

	public void salvar(Ajudante ajudante);

	List<Ajudante> findAllisAtivo();
}
