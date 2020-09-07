package com.edfer.service.logistica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.logistica.EstadoPneu;
import com.edfer.model.logistica.Pneu;
import com.edfer.repository.logistica.EstadoPneuRepository;
import com.edfer.repository.logistica.PneuRepository;

@Service
@Transactional
public class EstadoPneuServiceImpl implements EstadoPneuService {

	@Autowired
	private EstadoPneuRepository repository;

	@Autowired
	private PneuRepository repositoryPneu;

	@Override
	public List<EstadoPneu> findAllByIdPneu(Long idPneu) {
		return repository.findAllByPneu_IdPneuOrderByDataReformaDesc(idPneu);
	}

	@Override
	public void salvar(Long idPneu, EstadoPneu estadoPneu) {
		estadoPneu.setPneu(repositoryPneu.getOne(idPneu));
		repository.save(estadoPneu);
	}

	@Override
	public void update(Long idPneu, Long idEstadoPneu, EstadoPneu estadoPneu) {
		Pneu pneu = repositoryPneu.getOne(idPneu);
		estadoPneu.setKmFinal(pneu.getKm());
		estadoPneu.setPneu(pneu);
		repository.save(estadoPneu);
	}
}
