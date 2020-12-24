package com.edfer.service.logistica;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.filtersDTO.FilterDTO;
import com.edfer.model.logistica.Abastecimento;
import com.edfer.repository.logistica.AbastecimentoRepository;

@Service
@Transactional
public class AbastecimentoServiceImpl implements AbastecimentoService{

	@Autowired
	private AbastecimentoRepository repository;

	@Override
	public void salvar(Abastecimento abastecimento) {
		Optional<Long> previous = repository.findMenorHodometro(abastecimento.getVeiculo().getIdVeiculo(), abastecimento.getHodometro());
		if(previous.isPresent()) {
			abastecimento.updateConsumoMedio(previous.get());			
		}
		repository.save(abastecimento);
	}

	@Override
	public void update(Long idAbastecimento, Abastecimento abastecimento) {
		repository.save(abastecimento);
	}

	@Override
	public void delete(Long idAbastecimento) {
		repository.deleteById(idAbastecimento);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Abastecimento> findById(Long idAbastecimento) {
		return repository.findById(idAbastecimento);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Abastecimento> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Abastecimento> findPreviousAbastecimento(Long idVeiculo) {
		return repository.findFirstByVeiculoIdVeiculoOrderByIdAbastecimentoDesc(idVeiculo);
	}

	@Override
	public List<Abastecimento> findPrevios30ByVeiculo(Long idVeiculo) {
		return repository.findFirst30ByVeiculoIdVeiculoOrderByDataAbastecimentoDesc(idVeiculo);
	}

	@Override
	@Transactional(readOnly = true)
	public FilterDTO findByCriteria(String rows, String first, String sortFiel, String sortOrder, String idAbastecimento,
			String veiculos, String abastecimentoDateMin, String abastecimentoDateMax, String postos, String tipoCombustivel) {

		return repository.findByCriteria(Integer.parseInt(rows), Integer.parseInt(first), sortFiel, sortOrder, idAbastecimento, veiculos, abastecimentoDateMin, abastecimentoDateMax, postos, tipoCombustivel);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Abastecimento> findPreviusAbastecimentoByDate(Long idVeiculo, LocalDateTime dataAbastecimento) {
		return repository.findTop1ByVeiculoIdVeiculoAndDataAbastecimentoLessThanOrderByDataAbastecimentoDesc(idVeiculo, dataAbastecimento);
	}

	@Override
	public Optional<Long> findByHodometroLess(Long idVeiculo, Long Hodometro) {
		return repository.findMenorHodometro(idVeiculo, Hodometro);
	}
}
