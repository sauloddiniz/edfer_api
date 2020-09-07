package com.edfer.service.logistica;

import java.util.List;
import java.util.Optional;

import com.edfer.model.logistica.FabricantePneu;

public interface FabricantePneuService {

	void salvar(FabricantePneu fabricantePneu);
	
	void update(Long idFabricantePneuId, FabricantePneu fabricantePneu);
	
	void delete(Long idFabricantePneuId);
	
	Optional<FabricantePneu> findById(Long idFabricantePneuId);
	
	List<FabricantePneu> findAll();	
}
