package com.edfer.repository.certificado;

import com.edfer.model.filtersDTO.FilterDTO;

public interface ProdutoCertificadoRepositoryExtends {
;
	FilterDTO findProdutoByCriteria(String first, String rows, String sortField, String sortOrder, 
			String codigo, String classe, String descricao);
}
