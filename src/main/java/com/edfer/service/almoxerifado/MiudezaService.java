package com.edfer.service.almoxerifado;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.almoxerifado.Miudeza;
import com.edfer.model.filtersDTO.FilterDTO;

@Transactional
public interface MiudezaService {

	void save(Miudeza miudeza);

	@Transactional(readOnly = true)
	List<Miudeza> findAllByCodigoStartingWith(String codigo);

	@Transactional(readOnly = true)
	FilterDTO findAllByCriterio(String first, String rows, String sortField, String sortOrder, String codigo, String descricao);

	@Transactional(readOnly = true)
	Optional<Miudeza> findOneByCodigo(String Codigo);

	@Transactional(readOnly = true)
	Optional<Miudeza> findById(Long idMiudeza);
}
