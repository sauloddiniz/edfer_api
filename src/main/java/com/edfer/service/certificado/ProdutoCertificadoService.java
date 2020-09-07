package com.edfer.service.certificado;

import java.util.List;
import java.util.Optional;

import com.edfer.model.certificado.ProdutoCertificado;
import com.edfer.model.certificado.ProdutoCertificadoAux;
import com.edfer.model.filtersDTO.FilterDTO;

public interface ProdutoCertificadoService {

	void save(ProdutoCertificado produtoCertificado);

	List<ProdutoCertificado> startingWithCodigo(Long codigo);

	ProdutoCertificado findById(Long idProdutoCertificado);

	Optional<ProdutoCertificado> findByCodigo(String codigo);

	FilterDTO findProdutoByCriteria(String first, String rows, String sortField, String sortOrder, String codigo,
			String classe, String descricao);
	
	List<ProdutoCertificadoAux> findAllProdutoAux();
}
