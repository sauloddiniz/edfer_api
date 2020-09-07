package com.edfer.repository.logistica;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.logistica.ParteDiaria;

@Repository
public interface ParteDiariaRepository extends JpaRepository<ParteDiaria, Long>, ParteDiariaRepositoryExtends{
	
	Optional<ParteDiaria> findByDataParteDiariaAndVeiculoIdVeiculoAndMotoristaIdFuncionario(LocalDate dataParteDiaira,
			Long idVeiculo, Long idFuncionario);
	
	@Transactional(readOnly = true)
	@Query(value = "SELECT p.kmRodado FROM ParteDiaria p WHERE p.idParteDiaria = :idParteDiaria")
	Optional<Long> kmParteDiaria(Long idParteDiaria);

}
