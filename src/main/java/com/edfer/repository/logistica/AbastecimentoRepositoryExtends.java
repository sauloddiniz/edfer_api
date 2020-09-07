package com.edfer.repository.logistica;

import java.util.List;

import com.edfer.model.filtersDTO.FilterDTO;
import com.edfer.model.logistica.Abastecimento;

public interface AbastecimentoRepositoryExtends {

	List<Abastecimento> findAllLimitOrderByDate(Long idVeiculo);

	FilterDTO findByCriteria(int rows, int first, String sortFiel, String sortOrder, String idAbastecimento,
			String veiculos, String abastecimentoDateMin, String abastecimentoDateMax, String postos, String tipoCombustivel);
}
