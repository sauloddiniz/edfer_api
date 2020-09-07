package com.edfer.service.logistica;

import java.util.List;

import com.edfer.model.logistica.HistoricoPneu;

public interface HistoricoPneuService {

	void salvar(HistoricoPneu historicoPneu);

	void update(Long idHistorico, HistoricoPneu historicoPneu);

	void delete(Long idPneu, Long idHistorico);

	List<HistoricoPneu> findAllByIdPneu(Long idPneu);
}
