package com.edfer.service.almoxerifado;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edfer.model.almoxerifado.Miudeza;
import com.edfer.model.filtersDTO.FilterDTO;
import com.edfer.repository.almoxerifado.MiudezaRepository;

@Service
public class MiudezaServiceImpl implements MiudezaService {

	@Autowired
	private MiudezaRepository repository;
	
	@Override
	public void save(Miudeza miudeza) {
		repository.save(miudeza);
	}

	@Override
	public List<Miudeza> findAllByCodigoStartingWith(String codigo) {
		return repository.findByCodigoStartingWith(codigo);
	}

	@Override
	public FilterDTO findAllByCriterio(String first, String rows, String sortField, String sortOrder, String codigo, String descricao) {
		return repository.findByCriteria(first, rows, sortField, sortOrder, codigo, descricao);
	}

	@Override
	public Optional<Miudeza> findOneByCodigo(String codigo) {
		return repository.findByCodigo(codigo);
	}

	@Override
	public Optional<Miudeza> findById(Long idMiudeza) {
		return repository.findById(idMiudeza);
	}

}
