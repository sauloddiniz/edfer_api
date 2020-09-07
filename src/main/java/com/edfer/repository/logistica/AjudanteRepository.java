package com.edfer.repository.logistica;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edfer.model.logistica.Ajudante;

@Repository
public interface AjudanteRepository extends JpaRepository<Ajudante, Long>{

	List<Ajudante> findAllByIsAtivoTrue();
}
