package com.edfer.repository.certificado;

import com.edfer.model.filtersDTO.FilterDTO;

public interface CertificadoRepositoryExtends {

	FilterDTO findCertificadoByCriteria(String first, String rows, String sortField, String sortOrder,
			 String codigo, String dateMin, String dateMax, String numero, String fornecedor, String norma, String corrida, String volume, String dimensao);
}
