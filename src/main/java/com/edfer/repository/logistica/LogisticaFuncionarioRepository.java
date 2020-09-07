package com.edfer.repository.logistica;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edfer.model.logistica.LogisticaFuncionario;

@Repository
public interface LogisticaFuncionarioRepository extends JpaRepository<LogisticaFuncionario, Long> {
	
	List<LogisticaFuncionario> findAllByIsAtivoTrue();
}
