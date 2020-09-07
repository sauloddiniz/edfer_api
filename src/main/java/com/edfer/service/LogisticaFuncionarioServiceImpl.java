package com.edfer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edfer.model.logistica.Ajudante;
import com.edfer.model.logistica.LogisticaFuncionario;
import com.edfer.model.logistica.Motorista;
import com.edfer.repository.logistica.LogisticaFuncionarioRepository;

@Service
public class LogisticaFuncionarioServiceImpl implements LogisticaFuncionarioService {

	@Autowired
	private LogisticaFuncionarioRepository repository;
	
	@Override
	public List<LogisticaFuncionario> findAll() {

		List<LogisticaFuncionario> list = repository.findAllByIsAtivoTrue();

		for (LogisticaFuncionario lista : list) {
			if (lista instanceof Motorista) {
				lista.setInstanceOf("Motorista");
			} else if (lista instanceof Ajudante) {
				lista.setInstanceOf("Ajudante");
			}
		}
		return list;
	}

	@Override
	public LogisticaFuncionario findById(Long idFuncionario) {
		Optional<LogisticaFuncionario> aux = repository.findById(idFuncionario);
		LogisticaFuncionario funcionario = aux.get();
		if (funcionario instanceof Motorista) {
			funcionario.setInstanceOf("Motorista");
		}else if (funcionario instanceof Ajudante) {
			funcionario.setInstanceOf("Ajudante");
		}
		return funcionario;
	}

}
