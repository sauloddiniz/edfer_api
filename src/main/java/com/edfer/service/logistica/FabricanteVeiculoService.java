package com.edfer.service.logistica;

import java.util.List;
import java.util.Optional;

import com.edfer.model.logistica.FabricanteVeiculo;

public interface FabricanteVeiculoService {

	void salvar(FabricanteVeiculo fabricante);

	void delete(Long idFabricante);

	void update(Long idFabricante, FabricanteVeiculo fabricante);

	Optional<FabricanteVeiculo> findById(Long idFabricante);

	Optional<FabricanteVeiculo> findByNome(String nome);

	List<FabricanteVeiculo> findAll();

}
