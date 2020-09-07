package com.edfer.repository.agenda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.edfer.model.Usuario;
import com.edfer.model.agenda.Agenda;
import com.edfer.model.enuns.StatusGender;
import com.edfer.model.enuns.TipoCliente;
import com.edfer.model.filtersDTO.FilterDTO;

public class AgendaRepositoryExtendsImpl implements AgendaRepositoryExtends {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public FilterDTO findAllByCriteria(int first, int rows, String nomes, Long idAgenda, String estados,
			TipoCliente tipoCliente, String cliente, String visitaDe, String visitaAte, String retornoDe,
			String retornoAte, String observacao, String sortField, String sortOrder, String orcamentoNumber,
			BigDecimal valorMin, BigDecimal valorMax) {
		
		DateTimeFormatter localDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter localDateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Agenda> criteriaQuery = criteriaBuilder.createQuery(Agenda.class);
		Root<Agenda> root = criteriaQuery.from(Agenda.class);
		criteriaQuery.distinct(true);

		if (!sortField.equals("")) {
			Path<?> attribOrder = root.get(sortField);
			if (sortOrder.equals("1")) {
				criteriaQuery.orderBy(criteriaBuilder.asc(attribOrder));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(attribOrder));
			}
		}

//		Join<Agenda, GenderDate> genderDate = root.join("genderDate", JoinType.INNER);
		Join<Agenda, Usuario> usuario = root.join("usuario", JoinType.INNER);

		List<Predicate> conditions = new ArrayList<Predicate>();

		if (!observacao.equals("")) {
			Path<String> attrObservacao = root.get("observacao");
			Predicate containsObservacao = criteriaBuilder.like(attrObservacao, "%" + observacao + "%");
			conditions.add(containsObservacao);
		}

		if (!visitaDe.equals("") && !visitaDe.equals("__/__/____")) {
			LocalDate dateMin = LocalDate.parse(visitaDe, localDateFormat);
			Path<LocalDate> attributVisitaDateMin = root.get("dataVisita");
			Predicate whereDateMin = criteriaBuilder.greaterThanOrEqualTo(attributVisitaDateMin, dateMin);
			conditions.add(whereDateMin);
		}

		if (!visitaAte.equals("") && !visitaAte.equals("__/__/____")) {
			LocalDate dateMax = LocalDate.parse(visitaAte, localDateFormat);
			Path<LocalDate> attributVisitaDateMax = root.get("dataVisita");
			Predicate whereDateMax = criteriaBuilder.lessThanOrEqualTo(attributVisitaDateMax, dateMax);
			conditions.add(whereDateMax);
		}

		if (!retornoDe.equals("") && !retornoDe.equals("__/__/____")) {
			LocalDateTime dateMin = LocalDateTime.parse(retornoDe, localDateTimeFormat);
			Path<LocalDateTime> attributRetornoDateMin = root.get("dataRetorno");
			Predicate whereDateRetMin = criteriaBuilder.greaterThanOrEqualTo(attributRetornoDateMin, dateMin);
			conditions.add(whereDateRetMin);
		}

		if (!retornoAte.equals("") && !retornoAte.equals("__/__/____")) {
			LocalDateTime dateMax = LocalDateTime.parse(retornoAte, localDateTimeFormat);
			Path<LocalDateTime> attributRetornoDateMax = root.get("dataRetorno");
			Predicate whereDateRetMax = criteriaBuilder.lessThanOrEqualTo(attributRetornoDateMax, dateMax);
			conditions.add(whereDateRetMax);
		}

		if (!estados.equals("")) {
			List<Long> listEstados = Stream.of(estados.split(",")).map(Long::parseLong).collect(Collectors.toList());
			Path<StatusGender> attributEstatus = root.get("status");
			Predicate containsStatus = criteriaBuilder.isTrue(attributEstatus.in(listEstados));
			conditions.add(containsStatus);
		}

		if (idAgenda != null) {
			Path<String> attributIdAgenda = root.get("idAgenda");
			Predicate whereIdAgenda = criteriaBuilder.equal(attributIdAgenda, idAgenda);
			conditions.add(whereIdAgenda);
		}

		if (tipoCliente != null) {
			Path<String> attributTipoCliente = root.get("tipoCliente");
			Predicate whereTipoCliente = criteriaBuilder.equal(attributTipoCliente, tipoCliente);
			conditions.add(whereTipoCliente);
		}

		if (!cliente.equals("")) {
			Path<String> attributCliente = root.get("cliente");
			Predicate containsCliente = criteriaBuilder.like(attributCliente, "%" + cliente + "%");
			conditions.add(containsCliente);
		}

		if (!nomes.equals("")) {
			List<String> usuarios = Stream.of(nomes.split(",")).collect(Collectors.toList());
			Path<String> attribEmailUser = usuario.get("email");
			Predicate containsEmail = criteriaBuilder.isTrue(attribEmailUser.in(usuarios));
			conditions.add(containsEmail);
		}

		if (!orcamentoNumber.equals("")) {
			Path<String> attrOrcamentoNumber = root.get("orcamentoNumber");
			Predicate likeOrcamentoNumber = criteriaBuilder.like(attrOrcamentoNumber, orcamentoNumber);
			conditions.add(likeOrcamentoNumber);
		}

		if (valorMin != null) {
			Path<BigDecimal> attribuValorMin = root.get("valorOrcamento");
			Predicate whereValorMin = criteriaBuilder.greaterThanOrEqualTo(attribuValorMin, valorMin);
			conditions.add(whereValorMin);
		}

		if (valorMax != null) {
			Path<BigDecimal> attribuValorMax = root.get("valorOrcamento");
			Predicate whereValorMax = criteriaBuilder.lessThanOrEqualTo(attribuValorMax, valorMax);
			conditions.add(whereValorMax);
		}

		Predicate[] conditionsToArray = conditions.toArray(new Predicate[conditions.size()]);

		Predicate allConditions = criteriaBuilder.and(conditionsToArray);

		criteriaQuery.where(allConditions);

		TypedQuery<Agenda> query = manager.createQuery(criteriaQuery);

		Integer resultCont = query.getResultList().size();

		query.setFirstResult(first);
		query.setMaxResults(rows);

		FilterDTO filterDTO = new FilterDTO(resultCont, query.getResultList());

		return filterDTO;
	}

	@Override
	public FilterDTO findAllCriteriaByVendedor(int first, int rows, String email, Long idAgenda, String estados,
			TipoCliente tipoCliente, String cliente, String visitaDe, String visitaAte, String retornoDe,
			String retornoAte, String observacao, String sortField, String sortOrder, String orcamentoNumber,
			BigDecimal valorMin, BigDecimal valorMax) {
		
		DateTimeFormatter localDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter localDateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Agenda> criteriaQuery = criteriaBuilder.createQuery(Agenda.class);
		Root<Agenda> root = criteriaQuery.from(Agenda.class);
		criteriaQuery.distinct(true);

		if (!sortField.equals("")) {
			Path<?> attribOrder = root.get(sortField);
			if (sortOrder.equals("1")) {
				criteriaQuery.orderBy(criteriaBuilder.asc(attribOrder));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(attribOrder));
			}
		}

//		Join<Agenda, GenderDate> genderDate = root.join("genderDate", JoinType.INNER);
		Join<Agenda, Usuario> usuario = root.join("usuario", JoinType.INNER);

		List<Predicate> conditions = new ArrayList<Predicate>();
		
		if (!email.equals("")) {
			Path<String> attrUsuarioEmail = usuario.get("email");
			Predicate equalUsurioEmail = criteriaBuilder.equal(attrUsuarioEmail, email);
			conditions.add(equalUsurioEmail);
		}

		if (idAgenda != null) {
			Path<Long> attrIdAgenda = root.get("idAgenda");
			Predicate equalIdAgenda = criteriaBuilder.equal(attrIdAgenda, idAgenda);
			conditions.add(equalIdAgenda);
		}
		
		if (!observacao.equals("")) {
			Path<String> attrObservacao = root.get("observacao");
			Predicate containsObservacao = criteriaBuilder.like(attrObservacao, "%" + observacao + "%");
			conditions.add(containsObservacao);
		}

		if (!visitaDe.equals("") && !visitaDe.equals("__/__/____")) {
			LocalDate dateMin = LocalDate.parse(visitaDe, localDateFormat);
			Path<LocalDate> attributVisitaDateMin = root.get("dataVisita");
			Predicate whereDateMin = criteriaBuilder.greaterThanOrEqualTo(attributVisitaDateMin, dateMin);
			conditions.add(whereDateMin);
		}

		if (!visitaAte.equals("") && !visitaAte.equals("__/__/____")) {
			LocalDate dateMax = LocalDate.parse(visitaAte, localDateFormat);
			Path<LocalDate> attributVisitaDateMax = root.get("dataVisita");
			Predicate whereDateMax = criteriaBuilder.lessThanOrEqualTo(attributVisitaDateMax, dateMax);
			conditions.add(whereDateMax);
		}

		if (!retornoDe.equals("") && !retornoDe.equals("__/__/____")) {
			LocalDateTime dateMin = LocalDateTime.parse(retornoDe, localDateTimeFormat);
			Path<LocalDateTime> attributRetornoDateMin = root.get("dataRetorno");
			Predicate whereDateRetMin = criteriaBuilder.greaterThanOrEqualTo(attributRetornoDateMin, dateMin);
			conditions.add(whereDateRetMin);
		}

		if (!retornoAte.equals("") && !retornoAte.equals("__/__/____")) {
			LocalDateTime dateMax = LocalDateTime.parse(retornoAte, localDateTimeFormat);
			Path<LocalDateTime> attributRetornoDateMax = root.get("dataRetorno");
			Predicate whereDateRetMax = criteriaBuilder.lessThanOrEqualTo(attributRetornoDateMax, dateMax);
			conditions.add(whereDateRetMax);
		}

		if (!estados.equals("")) {
			List<Long> listEstados = Stream.of(estados.split(",")).map(Long::parseLong).collect(Collectors.toList());
			Path<StatusGender> attributEstatus = root.get("status");
			Predicate containsStatus = criteriaBuilder.isTrue(attributEstatus.in(listEstados));
			conditions.add(containsStatus);
		}

		if (tipoCliente != null) {
			Path<String> attributTipoCliente = root.get("tipoCliente");
			Predicate whereTipoCliente = criteriaBuilder.equal(attributTipoCliente, tipoCliente);
			conditions.add(whereTipoCliente);
		}

		if (!cliente.equals("")) {
			Path<String> attributCliente = root.get("cliente");
			Predicate containsCliente = criteriaBuilder.like(attributCliente, "%" + cliente + "%");
			conditions.add(containsCliente);
		}

		if (!orcamentoNumber.equals("")) {
			Path<String> attrOrcamentoNumber = root.get("orcamentoNumber");
			Predicate likeOrcamentoNumber = criteriaBuilder.like(attrOrcamentoNumber, orcamentoNumber);
			conditions.add(likeOrcamentoNumber);
		}

		if (valorMin != null) {
			Path<BigDecimal> attribuValorMin = root.get("valorOrcamento");
			Predicate whereValorMin = criteriaBuilder.greaterThanOrEqualTo(attribuValorMin, valorMin);
			conditions.add(whereValorMin);
		}

		if (valorMax != null) {
			Path<BigDecimal> attribuValorMax = root.get("valorOrcamento");
			Predicate whereValorMax = criteriaBuilder.lessThanOrEqualTo(attribuValorMax, valorMax);
			conditions.add(whereValorMax);
		}

		Predicate[] conditionsToArray = conditions.toArray(new Predicate[conditions.size()]);

		Predicate allConditions = criteriaBuilder.and(conditionsToArray);

		criteriaQuery.where(allConditions);

		TypedQuery<Agenda> query = manager.createQuery(criteriaQuery);

		Integer resultCont = query.getResultList().size();

		query.setFirstResult(first);
		query.setMaxResults(rows);

		FilterDTO filterDTO = new FilterDTO(resultCont, query.getResultList());

		return filterDTO;
	}

}
