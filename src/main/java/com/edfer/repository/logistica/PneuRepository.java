package com.edfer.repository.logistica;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.edfer.model.enuns.Estado;
import com.edfer.model.logistica.Pneu;

@Repository
public interface PneuRepository extends JpaRepository<Pneu, Long>, PneuRepositoryExtends {

	Optional<Pneu> findByNumSerie(String numSerie);

	Optional<List<Pneu>> findAllByVeiculo_idVeiculo(Long idVeiculo);
	
	@Modifying
	@Query("UPDATE Pneu p SET p.ativo = :ativo WHERE p.idPneu = :idPneu")
	void updateAtividade(boolean ativo, Long idPneu);

	@Modifying
	@Query("UPDATE Pneu p SET p.estadoAtual = :estadoAtual, p.km = :km WHERE p.idPneu = :idPneu")
	void updateEstado(Estado estadoAtual, Long km, Long idPneu);
	
	@Modifying
	@Query("UPDATE Pneu p SET p.km = :km WHERE p.idPneu = :idPneu")
	void updateKm(Long km, Long idPneu);

	List<Pneu> findAllByAtivo(boolean ativo);
}
