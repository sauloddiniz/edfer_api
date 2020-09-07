package com.edfer.service.logistica;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.filtersDTO.FilterDTO;
import com.edfer.model.logistica.Pneu;
import com.edfer.repository.logistica.PneuRepository;

@Service
@Transactional
public class PneuServiceImpl implements PneuService {

	@Autowired
	private PneuRepository repository;

	@Override
	public void salvar(Pneu pneu) {

		repository.save(pneu);

		if (pneu.getEstadosPneu() != null) {
			pneu.getEstadosPneu().parallelStream().forEach(pneu::addEstadoPneu);
		}
	}

	@Override
	public void deletar(Long idPneu) {
		repository.deleteById(idPneu);
	}

	@Override
	public void update(Pneu pneu) {
		repository.save(pneu);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Pneu> findById(Long idPneu) {
		return repository.findById(idPneu);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Pneu> findByNumSerie(String numSerie) {
		return repository.findByNumSerie(numSerie);
	}

	@Override
	public void updateAtivo(boolean ativo, Long idPneu) {
		repository.updateAtividade(ativo, idPneu);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Pneu> findAllByAtivo(boolean ativo) {
		return repository.findAllByAtivo(ativo);
	}

	@Override
	public Optional<List<Pneu>> findAllByVeiculo(Long idVeiculo) {
		return repository.findAllByVeiculo_idVeiculo(idVeiculo);
	}

	@Override
	public List<Pneu> findAll() {
		return repository.findAll();
	}

	@Override
	public FilterDTO findAllByCriteria(String sortField, String sortOrder, String first, String rows, String veiculos,
			String codControleEdfer, String kmMin, String kmMax, String compraPneuDateMin, String compraPneuDateMax,
			String modelo, String fabricantePneu, String estadoAtual, String numSerie,
			String numNotaOuNumOrdem, String step, String ativo) {

		return repository.findAllByCriteria(sortField, sortOrder, Integer.parseInt(first), Integer.parseInt(rows),
				veiculos, codControleEdfer, kmMin, kmMax, compraPneuDateMin, compraPneuDateMax, modelo,
				fabricantePneu, estadoAtual, numSerie, numNotaOuNumOrdem, step, ativo);
	}

}
