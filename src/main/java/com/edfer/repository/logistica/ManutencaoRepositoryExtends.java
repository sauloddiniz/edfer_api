package com.edfer.repository.logistica;

import com.edfer.model.filtersDTO.FilterDTO;

public interface ManutencaoRepositoryExtends {
	FilterDTO findAllByCriteria(String sortField, String sortOrder, int first, int rows, String idManutencao,
			String tipoManutencao, String servico, String valorMin, String valorMax, String tipoNota,
			String kmInicialMin, String kmInicialMax, String manutencaoDateMin, String manutencaoDateMax,
			String fornecedor, String numNotaOuNumOrdem, String veiculos);
}
