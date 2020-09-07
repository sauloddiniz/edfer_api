package com.edfer.service.logistica;

import java.util.List;

import com.edfer.model.logistica.EstadoPneu;

public interface EstadoPneuService {

	List<EstadoPneu> findAllByIdPneu(Long idPneu);

	void salvar(Long idPneu, EstadoPneu estadoPneu);

	void update(Long idPneu, Long idEstadoPneu, EstadoPneu estadoPneu);
}
