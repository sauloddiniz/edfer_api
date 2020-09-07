package com.edfer.service.logistica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.filtersDTO.FilterDTO;
import com.edfer.model.logistica.Manutencao;
import com.edfer.repository.logistica.ManutencaoRepository;

@Service
@Transactional
public class ManutencaoServiceImpl implements ManutencaoService {

	@Autowired
	private ManutencaoRepository repository;

	@Override
	public void salvar(Manutencao manutencao) {
		System.out.println(manutencao.getHodometroEfetuado());
		System.out.println(manutencao.getValidadeManutencao());
		repository.save(manutencao);
	}

	@Override
	public void update(Long idManutencao, Manutencao manutencao) {
		manutencao.setIdManutencao(idManutencao);
		repository.save(manutencao);
	}

	@Override
	public void delete(Long idManutencao) {
		repository.deleteById(idManutencao);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Manutencao> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Manutencao> findAllByIdVeiculo(Long idVeiculo) {
		return repository.findAllByVeiculo_IdVeiculo_OrderByDataManutencaoDesc(idVeiculo);
	}

	@Override
	public List<String> findAllForncedorStartingWith(String fornecedor) {
		return repository.findAllFornecedor(fornecedor);
	}

	@Override
	public FilterDTO findAllByCriteria(String sortField, String sortOrder, String first, String rows, String idManutencao,
			String tipoManutencao, String tipoServico, String valorMin, String valorMax, String tipoNota,
			String validHodometroMin, String validHodometroMax, String validManutencaoDateMin,
			String validManutencaoDateMax, String fornecedor, String numNotaOuNumOrdem, String veiculos) {

		return repository.findAllByCriteria(sortField, sortOrder, Integer.parseInt(first), Integer.parseInt(rows),
				idManutencao, tipoManutencao, tipoServico, valorMin, valorMax, tipoNota, validHodometroMin, validHodometroMax,
				validManutencaoDateMin, validManutencaoDateMax, fornecedor, numNotaOuNumOrdem, veiculos);
	}

	@Override
	public Manutencao findById(Long idVeiculo) {
		return repository.findById(idVeiculo).get();
	}

}
