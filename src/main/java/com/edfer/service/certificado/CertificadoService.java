package com.edfer.service.certificado;

import java.util.List;
import java.util.Optional;

import com.edfer.model.certificado.Certificado;
import com.edfer.model.filtersDTO.FilterDTO;

public interface CertificadoService {

	List<String> findFornecedores(String fornecedor);

	void save(Certificado certificado);

	public List<String> startingWithNorma(String norma);

	Optional<Certificado> findByIdCertificado(Long idCertificado);

	Optional<String> findByNumero(String numero);

	List<Certificado> findAllByIdProduto(Long idProduto);

	FilterDTO findCertificadoByCriteria(String first, String rows, String sortField, String sortOrder, String codigo,
			String dateMin, String dateMax, String numero, String fornecedor, String norma, String corrida,
			String volume, String dimensao);
}
