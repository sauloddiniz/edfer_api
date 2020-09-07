package com.edfer.repository.logistica;

import com.edfer.model.filtersDTO.FilterDTO;

public interface ParteDiariaRepositoryExtends {

	FilterDTO findByCriteria(int rows, int first, String sortField, String sortOrder, String veiculos,
			String dataParteDiariaMin, String dataParteDiariaMax, String cliente, String nota);
}
