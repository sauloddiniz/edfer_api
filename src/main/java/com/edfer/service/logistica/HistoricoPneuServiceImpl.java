package com.edfer.service.logistica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.logistica.HistoricoPneu;
import com.edfer.repository.logistica.HistoricoPneuRepository;

@Service
@Transactional
public class HistoricoPneuServiceImpl implements HistoricoPneuService {

	@Autowired
	private HistoricoPneuRepository repository;

	@Override
	public void salvar(HistoricoPneu historicoPneu) {
		repository.save(historicoPneu);
	}

	@Override
	public void update(Long idHistorico, HistoricoPneu historicoPneu) {
		historicoPneu.setIdHistorico(idHistorico);
		repository.save(historicoPneu);
	}

	@Override
	public void delete(Long idPneu, Long idHistorico) {
		repository.deleteById(idHistorico);
	}

	@Override
	public List<HistoricoPneu> findAllByIdPneu(Long idPneu) {
		return repository.findAllByPneu_IdPneu(idPneu);
	}

}
