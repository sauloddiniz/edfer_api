package com.edfer.repository.logistica;

import java.util.List;

import com.edfer.model.filtersDTO.FilterDTO;
import com.edfer.model.logistica.Pneu;

public interface PneuRepositoryExtends {

	List<Pneu> findAllPneu();
	
	FilterDTO findAllByCriteria(String sortField, String sortOrder, int first, int rows, String veiculos,
			String codControleEdfer, String kmMin, String kmMax, String compraPneuDateMin, String compraPneuDateMax,
			String modelo, String fabricantePneu, String estadoAtual, String numSerie,
			String numNotaOuNumOrdem, String step, String ativo);
}
