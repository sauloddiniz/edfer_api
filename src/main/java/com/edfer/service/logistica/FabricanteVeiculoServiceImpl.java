package com.edfer.service.logistica;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.logistica.FabricanteVeiculo;
import com.edfer.repository.logistica.FabricanteVeiculoRepository;

@Service
@Transactional
public class FabricanteVeiculoServiceImpl implements FabricanteVeiculoService{

	@Autowired
	private FabricanteVeiculoRepository repository;
	
	@Override
	public void salvar(FabricanteVeiculo fabricante) {
		repository.save(fabricante);
	}

	@Override
	public void delete(Long idFabricante) {
		repository.deleteById(idFabricante);
	}

	@Override
	public void update(Long idFabricante, FabricanteVeiculo fabricante) {
		fabricante.setIdFabricante(idFabricante);
		repository.save(fabricante);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<FabricanteVeiculo> findById(Long idFabricante) {
		return repository.findById(idFabricante);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FabricanteVeiculo> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<FabricanteVeiculo> findByNome(String nome) {
		return repository.findByNome(nome);
	}

}
