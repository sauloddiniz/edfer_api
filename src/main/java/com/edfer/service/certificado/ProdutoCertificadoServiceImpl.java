package com.edfer.service.certificado;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.certificado.ProdutoCertificado;
import com.edfer.model.certificado.ProdutoCertificadoAux;
import com.edfer.model.filtersDTO.FilterDTO;
import com.edfer.repository.certificado.ProdutoCertificadoAuxRepository;
import com.edfer.repository.certificado.ProdutoCertificadoRepository;

@Service
@Transactional
public class ProdutoCertificadoServiceImpl implements ProdutoCertificadoService {

	@Autowired
	private ProdutoCertificadoRepository repository;

	@Autowired
	private ProdutoCertificadoAuxRepository produtoAuxRepository;
	
	@Override
	public void save(ProdutoCertificado produtoCertificado) {
		repository.save(produtoCertificado);
	}

	@Transactional(readOnly = true)
	@Override
	public List<ProdutoCertificado> startingWithCodigo(Long codigo) {
		return repository.findByCodigoStartingWith(codigo);
	}

	@Override
	@Transactional(readOnly = true)
	public ProdutoCertificado findById(Long idProdutoCertificado) {
		return repository.getOne(idProdutoCertificado);
	}

	@Transactional(readOnly = true)
	@Override
	public FilterDTO findProdutoByCriteria(String first, String rows, String sortField, String sortOrder, 
			String codigo, String classe, String descricao) {

		return repository.findProdutoByCriteria(first, rows, sortField, sortOrder,
				codigo, classe, descricao);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<ProdutoCertificado> findByCodigo(String codigo) {
		return repository.findByCodigo(codigo);
	}

	@Override
	public List<ProdutoCertificadoAux> findAllProdutoAux() {
		return produtoAuxRepository.findAllDistinct();
	}
}
