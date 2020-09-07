package com.edfer.service;

import java.util.List;

import com.edfer.model.logistica.LogisticaFuncionario;

public interface LogisticaFuncionarioService {

	List<LogisticaFuncionario> findAll();

	LogisticaFuncionario findById(Long idFuncionario);
}
