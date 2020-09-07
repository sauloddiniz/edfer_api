package com.edfer.repository.almoxerifado;

import com.edfer.model.filtersDTO.FilterDTO;

public interface MiudezaRepositoryExtends {

	FilterDTO findByCriteria(String first, String rows, String sortField, String sortOrder, String codigo, String desrcicao);
}
