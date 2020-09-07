package com.edfer.repository.extend;

import com.edfer.model.filtersDTO.FilterDTO;

public interface ProdutoRepositoryExtends {

	FilterDTO findAllFilterCriteria(int first, int rows, String codigo, String descricao, String sortField, String sortOrder);
}
