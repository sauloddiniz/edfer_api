package com.edfer.service.agenda;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.agenda.Agenda;
import com.edfer.model.agenda.GenderDate;
import com.edfer.model.enuns.TipoCliente;
import com.edfer.model.filtersDTO.FilterDTO;
import com.edfer.repository.UsuarioRepository;
import com.edfer.repository.agenda.AgendaRepository;

@Service
@Transactional
public class AgendaServiceImpl implements AgendaService {

	@Autowired
	private AgendaRepository repository;

	@Autowired
	UsuarioRepository userRepository;

	@Override
	public void salvar(Agenda agenda) {

		Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		agenda.setUsuario(userRepository.findByEmail(details.toString()).get());

		repository.save(agenda);

		if (agenda.getIdAgenda() != null) {
			GenderDate genderDate = new GenderDate(null, agenda.getDataVisita(), agenda.getDataRetorno(),
					agenda.getStatus(), agenda.getObservacao(), null);
			agenda.addDates(genderDate);
		}
	}

	@Override
	public void update(Agenda agenda, Long idAgenda) {

		Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		agenda.setUsuario(userRepository.findByEmail(details.toString()).get());

		agenda.setIdAgenda(idAgenda);

		if (agenda.getIdAgenda() != null) {
			GenderDate genderDate = new GenderDate(null, agenda.getDataVisita(), agenda.getDataRetorno(),
					agenda.getStatus(), agenda.getObservacao(), null);
			agenda.addDates(genderDate);
		}

		repository.save(agenda);

	}

	@Override
	public void delete(Long idAgenda) {
		repository.deleteById(idAgenda);
	}

	@Override
	@Transactional(readOnly = true)
	public FilterDTO filterByCriteria(String first, String rows, String nomes, String idAgenda, String estados,
			String tipoCliente, String cliente, String visitaDe, String visitaAte, String retornoDe, String retornoAte,
			String observacao, String orcamentoNumber, String valorMin, String valorMax, String sortField,
			String sortOrder) {

		// CONVERTER idAgenda
		Long idAgendaConverter = null;
		if (!idAgenda.equals("")) {
			idAgendaConverter = Long.valueOf(idAgenda);
		}

		TipoCliente tipoClienteConverter = null;
		if (tipoCliente.equals("1")) {
			tipoClienteConverter = TipoCliente.JURIDICA;
		} else if (tipoCliente.equals("0")) {
			tipoClienteConverter = TipoCliente.FISICA;
		}

		BigDecimal valorMinAux = null; 
		if (!valorMin.equals("")) {
			valorMinAux = BigDecimal.valueOf(Double.parseDouble(valorMin.replace(",", ".")));
		}
		
		BigDecimal valorMaxAux = null; 
		if (!valorMax.equals("")) {
			valorMaxAux = BigDecimal.valueOf(Double.parseDouble(valorMax.replace(",", ".")));
		}
		
		if (sortField.equals("dtVisita")) {
			sortField = "dataVisita";
		}

		if (sortField.equals("estados")) {
			sortField = "status";
		}

		if (sortField.equals("dtRetorno")) {
			sortField = "dataRetorno";
		}
		
		return repository.findAllByCriteria(Integer.parseInt(first), Integer.parseInt(rows), nomes, idAgendaConverter,
				estados, tipoClienteConverter, cliente, visitaDe, visitaAte, retornoDe, retornoAte, observacao,
				sortField, sortOrder, orcamentoNumber, valorMinAux, valorMaxAux);
	}

	@Override
	@Transactional(readOnly = true)
	public FilterDTO findAllCriteriaByVendedor(String first, String rows, String idAgenda, String estados,
			String tipoCliente, String cliente, String visitaDe, String visitaAte, String retornoDe,
			String retornoAte, String observacao, String sortField, String sortOrder, String orcamentoNumber,
			String valorMin, String valorMax) {

		TipoCliente tipoClienteConverter = null;
		if (tipoCliente.equals("1")) {
			tipoClienteConverter = TipoCliente.JURIDICA;
		} else if (tipoCliente.equals("0")) {
			tipoClienteConverter = TipoCliente.FISICA;
		}
		
		Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = details.toString();
		
		BigDecimal valorMinAux = null; 
		if (!valorMin.equals("")) {
			valorMinAux = BigDecimal.valueOf(Double.parseDouble(valorMin.replace(",", ".")));
		}
		
		BigDecimal valorMaxAux = null; 
		if (!valorMax.equals("")) {
			valorMaxAux = BigDecimal.valueOf(Double.parseDouble(valorMax.replace(",", ".")));
		}

		Long idAgendaAux = null; 
		if (!idAgenda.equals("")) {
			idAgendaAux = Long.parseLong(idAgenda);
		}

		if (sortField.equals("dtVisita")) {
			sortField = "dataVisita";
		}

		if (sortField.equals("estados")) {
			sortField = "status";
		}

		if (sortField.equals("dtRetorno")) {
			sortField = "dataRetorno";
		}
		
		return repository.findAllCriteriaByVendedor(Integer.parseInt(first), Integer.parseInt(rows), email, idAgendaAux, estados, tipoClienteConverter, cliente, visitaDe,
				visitaAte, retornoDe, retornoAte, observacao, sortField, sortOrder, orcamentoNumber, valorMinAux,
				valorMaxAux);
	}

	@Override
	public List<String> findCliente(String cliente) {
		return repository.findCliente(cliente);
	}

	@Override
	public List<String> findContato(String contato) {
		return repository.findContato(contato);
	}

}
