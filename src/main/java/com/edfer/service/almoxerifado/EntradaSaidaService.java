package com.edfer.service.almoxerifado;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.almoxerifado.EntradaSaidaMiudeza;

@Transactional
public interface EntradaSaidaService {

	void save(EntradaSaidaMiudeza entradaSaidaMiudeza);

	@Transactional(readOnly = true)
	List<EntradaSaidaMiudeza> findAll();

	void update(Long idEntradaSaida);
}
