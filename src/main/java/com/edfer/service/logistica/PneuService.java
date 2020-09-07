package com.edfer.service.logistica;

import java.util.List;
import java.util.Optional;

import com.edfer.model.filtersDTO.FilterDTO;
import com.edfer.model.logistica.Pneu;

public interface PneuService {

	void salvar(Pneu pneu);

	void deletar(Long idPneu);

	void update(Pneu pneu);

	void updateAtivo(boolean ativo, Long idPneu);

	Optional<Pneu> findById(Long idPneu);

	Optional<Pneu> findByNumSerie(String numSerie);

	List<Pneu> findAll();
	
	List<Pneu> findAllByAtivo(boolean ativo);

	Optional<List<Pneu>> findAllByVeiculo(Long idVeiculo);

	FilterDTO findAllByCriteria(String sortField, String sortOrder, String first, String rows, String veiculos,
			String codControleEdfer, String kmMin, String kmMax, String compraPneuDateMin, String compraPneuDateMax,
			String modelo, String fabricantePneu, String estadoAtual, String numSerie,
			String numNotaOuNumOrdem, String step, String ativo);
}
