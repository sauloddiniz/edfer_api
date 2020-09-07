package com.edfer.repository.logistica;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edfer.model.logistica.Motorista;

@Repository
public interface MotoristaRepository extends JpaRepository<Motorista, Long>{

	List<Motorista> findAllByIsAtivoTrue();

}
