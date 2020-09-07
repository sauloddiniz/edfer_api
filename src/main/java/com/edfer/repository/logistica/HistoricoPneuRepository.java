package com.edfer.repository.logistica;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edfer.model.logistica.HistoricoPneu;

@Repository
public interface HistoricoPneuRepository extends JpaRepository<HistoricoPneu, Long>{

	List<HistoricoPneu> findAllByPneu_IdPneu(Long idPneu);
}
