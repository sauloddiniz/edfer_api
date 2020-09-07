package com.edfer.service.certificado;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edfer.model.certificado.Certificado;
import com.edfer.model.certificado.ProdutoCertificadoAux;
import com.edfer.model.filtersDTO.FilterDTO;
import com.edfer.repository.certificado.CertificadoRepository;

@Service
public class CertificadoServiceImpl implements CertificadoService {

	@Autowired
	private CertificadoRepository repository;

	@Override
	public List<String> findFornecedores(String fornecedor) {
		return repository.findFornecedor(fornecedor);
	}

	@Override
	public void save(Certificado certificado) {

		for (ProdutoCertificadoAux aux : certificado.getProdutoCertificadoAux()) {
			aux.setCertificado(certificado);
			certificado.addProduto(aux.getProduto());
		}

		repository.saveAndFlush(certificado);
	}

	@Override
	public FilterDTO findCertificadoByCriteria(String first, String rows, String sortField, String sortOrder,
			String codigo, String dateMin, String dateMax, String numero, String fornecedor, String norma,
			String corrida, String volume, String dimensao) {

		return repository.findCertificadoByCriteria(first, rows, sortField, sortOrder, codigo, dateMin, dateMax, numero,
				fornecedor, norma, corrida, volume, dimensao);
	}

	@Override
	public List<String> startingWithNorma(String norma) {
		return repository.findNorma(norma);
	}

	@Override
	public List<Certificado> findAllByIdProduto(Long idProduto) {
		List<Certificado> certificados = repository.findAllByProdutos_IdProdutoCertificado(idProduto);
		for (Certificado certificado : certificados) {
			List<ProdutoCertificadoAux> list = new ArrayList<>();
			for (ProdutoCertificadoAux produtoCertificadoAux : certificado.getProdutoCertificadoAux()) {
				if (produtoCertificadoAux.getProduto().getIdProdutoCertificado().equals(idProduto)) {
					list.add(produtoCertificadoAux);
				}
				certificado.setProdutoCertificadoAux(list);
			}
		}
		return certificados;
	}

	@Override
	public Optional<String> findByNumero(String numero) {
		return repository.findByNumero(numero);
	}

	@Override
	public Optional<Certificado> findByIdCertificado(Long idCertificado) {
		return repository.findById(idCertificado);
	}

}
