package com.edfer.repository.logistica;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edfer.model.logistica.Posto;

@Repository
public interface PostoRepository extends JpaRepository<Posto, Long>{
	
	Optional<Posto> findByNomeIgnoreCase(String nome);

}
