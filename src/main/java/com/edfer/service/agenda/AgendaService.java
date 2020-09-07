package com.edfer.service.agenda;

import java.util.List;

import com.edfer.model.agenda.Agenda;
import com.edfer.model.filtersDTO.FilterDTO;

public interface AgendaService {

	void salvar(Agenda agenda);

	void update(Agenda agenda, Long idAgenda);

	void delete(Long idAgenda);

	List<String> findCliente(String cliente);

	List<String> findContato(String contato);

	FilterDTO filterByCriteria(String first, String rows, String nomes, String idAgenda, String estados,
			String tipoCliente, String cliente, String visitaDe, String visitaAte, String retornoDe, String retornoAte,
			String observacao, String orcamentoNumber, String valorMin, String valorMax, String sortField,
			String sortOrder);

	FilterDTO findAllCriteriaByVendedor(String first, String rows, String idAgenda, String estados, String tipoCliente,
			String cliente, String visitaDe, String visitaAte, String retornoDe, String retornoAte, String observacao,
			String sortField, String sortOrder, String orcamentoNumber, String valorMin, String valorMax);
}
