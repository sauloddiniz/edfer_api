package com.edfer.repository.logistica;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edfer.model.logistica.FabricanteVeiculo;

@Repository
public interface FabricanteVeiculoRepository extends JpaRepository<FabricanteVeiculo, Long> {

	Optional<FabricanteVeiculo> findByNome(String nome);
}
