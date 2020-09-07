package com.edfer.service.logistica;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.edfer.model.filtersDTO.FilterDTO;
import com.edfer.model.logistica.ParteDiaria;

@Service
public interface ParteDiariaService {

	void salvar(ParteDiaria parteDiaria);

	void updateParteDiaria(ParteDiaria parteDiaria);

	void delete(Long idParteDiaria);

	void update(Long idParteDiaria, ParteDiaria parteDiaria);

	ParteDiaria findByIdParteDiaria(Long idParteDiaria);

	List<ParteDiaria> findAll();
	
	FilterDTO findByCriteria(String rows, String first, String sortField, String sortOrder, String veiculos,
			String dataParteDiariaMin, String dataParteDiariaMax, String cliente, String nota);

	Optional<ParteDiaria> findByDataAndVeiculo(LocalDate dataParteDiaira, Long idVeiculo, Long idFuncionario);
}
