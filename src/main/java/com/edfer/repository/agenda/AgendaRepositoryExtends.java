package com.edfer.repository.agenda;

import java.math.BigDecimal;

import com.edfer.model.enuns.TipoCliente;
import com.edfer.model.filtersDTO.FilterDTO;

public interface AgendaRepositoryExtends {

	FilterDTO findAllByCriteria(int first, int rows, String nomes, Long idAgenda, String estados,
			TipoCliente tipoCliente, String cliente, String visitaDe, String visitaAte, String retornoDe,
			String retornoAte, String observacao, String sortField, String sortOrder,  String orcamentoNumber, BigDecimal valorMin, BigDecimal valorMax);
	
	FilterDTO findAllCriteriaByVendedor(int first, int rows, String email, Long idAgenda, String estados,
			TipoCliente tipoCliente, String cliente, String visitaDe, String visitaAte, String retornoDe,
			String retornoAte, String observacao, String sortField, String sortOrder, String orcamentoNumber,
			BigDecimal valorMin, BigDecimal valorMax);
}
