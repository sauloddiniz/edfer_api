package com.edfer.service.logistica;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.edfer.model.filtersDTO.FilterDTO;
import com.edfer.model.logistica.Abastecimento;

@Service
public interface AbastecimentoService {

	void salvar(Abastecimento abastecimento);

	void update(Long idAbastecimento, Abastecimento abastecimento);

	void delete(Long idAbastecimento);

	Optional<Abastecimento> findById(Long idAbastecimento);

	List<Abastecimento> findAll();

	List<Abastecimento> findPrevios30ByVeiculo(Long idVeiculo);

	Optional<Abastecimento> findPreviousAbastecimento(Long idAbastecimento);

	FilterDTO findByCriteria(String rows, String first, String sortField, String sortOrder, String idAbastecimento,
			String veiculos, String abastecimentoDateMin, String abastecimentoDateMax, String postos, String tipoCombustivel);
	
	Optional<Abastecimento> findPreviusAbastecimentoByDate(Long idVeiculo, LocalDateTime dataAbastecimento);

	Optional<Long> findByHodometroLess(Long idVeiculo, Long Hodometro);
}
