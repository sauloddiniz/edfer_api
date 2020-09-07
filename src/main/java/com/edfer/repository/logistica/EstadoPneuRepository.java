package com.edfer.repository.logistica;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.edfer.model.logistica.EstadoPneu;

@Repository
public interface EstadoPneuRepository extends JpaRepository<EstadoPneu, Long> {

	List<EstadoPneu> findAllByPneu_IdPneuOrderByDataReformaDesc(Long idPneu);

	@Modifying
	@Query("UPDATE EstadoPneu e SET e.kmFinal = :kmFinal WHERE e.idEstadoPneu = :idEstadoPneu")
	public void alterarEstado(Long kmFinal, Long idEstadoPneu);
}
