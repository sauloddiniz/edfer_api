package com.edfer.service.logistica;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.logistica.FabricantePneu;
import com.edfer.repository.logistica.FabricantePneuRepository;

@Service
@Transactional
public class FabricantePneuServiceImpl implements FabricantePneuService {

	@Autowired
	private FabricantePneuRepository repository;
	
	@Override
	public void salvar(FabricantePneu fabricantePneu) {
		repository.save(fabricantePneu);
	}

	@Override
	public void update(Long idFabricantePneuId, FabricantePneu fabricantePneu) {
		repository.save(fabricantePneu);
	}

	@Override
	public void delete(Long idFabricantePneuId) {
		repository.deleteById(idFabricantePneuId);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<FabricantePneu> findById(Long idFabricantePneuId) {
		return repository.findById(idFabricantePneuId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FabricantePneu> findAll() {
		return repository.findAll();
	}

}
