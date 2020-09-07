package com.edfer.repository.extend;

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

import com.edfer.model.EntradaPedido;
import com.edfer.model.Pedido;
import com.edfer.model.enuns.EnumSituacaoFinalPedido;
import com.edfer.model.enuns.StatusGender;
import com.edfer.model.filtersDTO.EntradaPedidoDTO;

public class EntradaPedidoRepositoryExtendsImpl implements EntradaPedidoRepositoryExtends {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public EntradaPedidoDTO findByCriteria(String sortField, String sortOrder, int rows, int first, String numeroPedido,
			String cliente, String rota, String localizacao, String pesoInicio, String pesoFinal, String previsaoInicio,
			String previsaoFinal, String status, String localizacaoPedido, String situacaoFinal, String dataCriacaoInicio, String dataCriacaoFinal) {

		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<EntradaPedido> criteriaQuery = criteriaBuilder.createQuery(EntradaPedido.class);
		Root<EntradaPedido> root = criteriaQuery.from(EntradaPedido.class);
		criteriaQuery.distinct(false);

		DateTimeFormatter localDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter localDateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		Join<EntradaPedido, Pedido> pedido = root.join("pedido", JoinType.LEFT);

		if (!sortField.equals("")) {
			if (sortField.equals("idEntradaPedido") || sortField.equals("numeroPedido") || sortField.equals("cliente") || sortField.equals("createdDate")) {
				Path<?> attribOrder = root.get(sortField);
				if (sortOrder.equals("1")) {
					criteriaQuery.orderBy(criteriaBuilder.asc(attribOrder));
				} else {
					criteriaQuery.orderBy(criteriaBuilder.desc(attribOrder));
				}
			} else {
				Path<?> attribOrder = pedido.get(sortField);
				if (sortOrder.equals("1")) {
					criteriaQuery.orderBy(criteriaBuilder.asc(attribOrder));
				} else {
					criteriaQuery.orderBy(criteriaBuilder.desc(attribOrder));
				}
			}
		}

//		Join<Agenda, Usuario> usuario = root.join("usuario", JoinType.INNER);

		List<Predicate> conditions = new ArrayList<Predicate>();

		if (!numeroPedido.equals("")) {
			Path<String> attrNumeroPedido = root.get("numeroPedido");
			Predicate equalNumeroPedido = criteriaBuilder.like(attrNumeroPedido, numeroPedido + "%");
			conditions.add(equalNumeroPedido);
		}

		if (!cliente.equals("")) {
			Path<String> attrCliente = root.get("cliente");
			Predicate containsCliente = criteriaBuilder.like(attrCliente, "%" + cliente + "%");
			conditions.add(containsCliente);
		}

		if (!dataCriacaoInicio.equals("") && !dataCriacaoInicio.equals("__/__/____ 00:00")) {
			LocalDateTime dataCriacaoInicioAux = LocalDateTime.parse(dataCriacaoInicio, localDateTimeFormat);
			Path<LocalDateTime> attrCreateDate = root.get("createdDate");
			Predicate whereDateCreateMin = criteriaBuilder.greaterThanOrEqualTo(attrCreateDate, dataCriacaoInicioAux);
			conditions.add(whereDateCreateMin);
		}

		if (!dataCriacaoFinal.equals("") && !dataCriacaoFinal.equals("__/__/____ 00:00")) {
			LocalDateTime dataCriacaoFinalAux = LocalDateTime.parse(dataCriacaoFinal, localDateTimeFormat);
			Path<LocalDateTime> attrCreateDateMax = root.get("createdDate");
			Predicate whereDateCreateMax = criteriaBuilder.greaterThanOrEqualTo(attrCreateDateMax, dataCriacaoFinalAux);
			conditions.add(whereDateCreateMax);
		}

		if (!rota.equals("")) {
			Path<String> attrRota = pedido.get("rota");
			Predicate containsRota = criteriaBuilder.like(attrRota, "%" + rota + "%");
			conditions.add(containsRota);
		}

		if (!localizacao.equals("")) {
			Path<String> attrLocalizacao = pedido.get("localizacao");
			Predicate contaisLocalizacao = criteriaBuilder.like(attrLocalizacao, "%" + localizacao + "%");
			conditions.add(contaisLocalizacao);
		}

		if (!pesoInicio.equals("")) {
			double psInicio = Double.parseDouble(pesoInicio);
			Path<Double> attrPesoInicio = pedido.get("pesoPedido");
			Predicate gtPesoInicio = criteriaBuilder.greaterThanOrEqualTo(attrPesoInicio, psInicio);
			conditions.add(gtPesoInicio);
		}

		if (!pesoFinal.equals("")) {
			double psFinal = Double.parseDouble(pesoFinal);
			Path<Double> attrPesoFinal = pedido.get("pesoPedido");
			Predicate ltPesoFinal = criteriaBuilder.lessThanOrEqualTo(attrPesoFinal, psFinal);
			conditions.add(ltPesoFinal);
		}

		if (!previsaoInicio.equals("") && !previsaoInicio.equals("__/__/____")) {
			LocalDate previsaoMin = LocalDate.parse(previsaoInicio, localDateFormat);
			Path<LocalDate> atttrPrevisaoMin = pedido.get("dataPrevista");
			Predicate whereDateMin = criteriaBuilder.greaterThanOrEqualTo(atttrPrevisaoMin, previsaoMin);
			conditions.add(whereDateMin);
		}

		if (!previsaoFinal.equals("") && !previsaoFinal.equals("__/__/____")) {
			LocalDate previsaoMax = LocalDate.parse(previsaoFinal, localDateFormat);
			Path<LocalDate> atttrPrevisaoMax = pedido.get("dataPrevista");
			Predicate whereDateMax = criteriaBuilder.lessThanOrEqualTo(atttrPrevisaoMax, previsaoMax);
			conditions.add(whereDateMax);
		}

		if (!status.equals("")) {
			List<Long> listStatus = Stream.of(status.split(",")).map(Long::parseLong).collect(Collectors.toList());
			Path<StatusGender> attributEstatus = pedido.get("status");
			Predicate containsStatus = criteriaBuilder.isTrue(attributEstatus.in(listStatus));
			conditions.add(containsStatus);
		}

		if (!localizacaoPedido.equals("")) {
			List<Long> listLocalizacao = Stream.of(localizacaoPedido.split(",")).map(Long::parseLong)
					.collect(Collectors.toList());
			Path<StatusGender> attributEstatus = pedido.get("localizacaoPedido");
			Predicate containsLocalizacaoPedido = criteriaBuilder.isTrue(attributEstatus.in(listLocalizacao));
			conditions.add(containsLocalizacaoPedido);
		}
		
		if (!situacaoFinal.equals("")) {
			List<Long> listSituacaoFinal = Stream.of(situacaoFinal.split(",")).map(Long::parseLong)
					.collect(Collectors.toList());
			Path<EnumSituacaoFinalPedido> attributSituacaoFinal = pedido.get("situacaoFinal");
			Predicate containsLSituacaoFinalo = criteriaBuilder.isTrue(attributSituacaoFinal.in(listSituacaoFinal));
			conditions.add(containsLSituacaoFinalo);
		}

		Predicate[] conditionsToArray = conditions.toArray(new Predicate[conditions.size()]);

		Predicate allConditions = criteriaBuilder.and(conditionsToArray);

		criteriaQuery.where(allConditions);

		TypedQuery<EntradaPedido> query = manager.createQuery(criteriaQuery);

		Integer resultCont = query.getResultList().size();

		query.setFirstResult(first);
		query.setMaxResults(rows);

		EntradaPedidoDTO entradaPedido = new EntradaPedidoDTO(resultCont, query.getResultList());

		return entradaPedido;
	}

}
