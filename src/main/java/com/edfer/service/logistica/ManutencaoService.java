package com.edfer.service.logistica;

import java.util.List;

import com.edfer.model.filtersDTO.FilterDTO;
import com.edfer.model.logistica.Manutencao;

public interface ManutencaoService {

	void salvar(Manutencao manutencao);

	void update(Long idManutencao, Manutencao manutencao);

	void delete(Long idManutencao);

	List<Manutencao> findAll();

	List<Manutencao> findAllByIdVeiculo(Long idVeiculo);

	List<String> findAllForncedorStartingWith(String fornecedor);

	Manutencao findById(Long idVeiculo);

	FilterDTO findAllByCriteria(String sortField, String sortOrder, String first, String rows, String idManutencao,
			String tipoManutencao, String tipoServico, String valorMin, String valorMax, String tipoNota,
			String validHodometroMin, String validHodometroMax, String validManutencaoDateMin,
			String validManutencaoDateMax, String fornecedor, String numNotaOuNumOrdem, String veiculos);
}
