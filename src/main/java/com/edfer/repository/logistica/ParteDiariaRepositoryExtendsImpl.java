package com.edfer.repository.logistica;

import java.time.LocalDate;
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

import com.edfer.model.filtersDTO.FilterDTO;
import com.edfer.model.logistica.ParteDiaria;
import com.edfer.model.logistica.RotaDiaria;
import com.edfer.model.logistica.Veiculo;

public class ParteDiariaRepositoryExtendsImpl implements ParteDiariaRepositoryExtends {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public FilterDTO findByCriteria(int rows, int first, String sortField, String sortOrder, String veiculos,
			String dataParteDiariaMin, String dataParteDiariaMax, String cliente, String nota) {

		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<ParteDiaria> criteriaQuery = criteriaBuilder.createQuery(ParteDiaria.class);
		Root<ParteDiaria> root = criteriaQuery.from(ParteDiaria.class);
		criteriaQuery.distinct(true);

		DateTimeFormatter localDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		// DateTimeFormatter localDateTimeFormat =
		// DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		Join<ParteDiaria, RotaDiaria> rota = root.join("rotaDiaria", JoinType.INNER);
		Join<ParteDiaria, Veiculo> veiculo = root.join("veiculo", JoinType.INNER);

		if (!sortField.equals("")) {
			Path<?> attribOrder = root.get(sortField);
			if (sortOrder.equals("1")) {
				criteriaQuery.orderBy(criteriaBuilder.asc(attribOrder));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(attribOrder));
			}
		}

		List<Predicate> conditions = new ArrayList<Predicate>();

		if (!veiculos.equals("")) {
			List<Long> listVeiculos = Stream.of(veiculos.split(",")).map(Long::parseLong).collect(Collectors.toList());
			Path<Long> attrVeiculo = veiculo.get("idVeiculo");
			Predicate containsVeiculos = criteriaBuilder.isTrue(attrVeiculo.in(listVeiculos));
			conditions.add(containsVeiculos);
		}

		if (!cliente.equals("")) {
			Path<String> attrCliente = rota.get("nomeClienteOuFornecedor");
			Predicate contaisCliente = criteriaBuilder.like(attrCliente, "%" + cliente + "%");
			conditions.add(contaisCliente);
		}

		if (!nota.equals("")) {
			Predicate notaEqual = criteriaBuilder.and(rota.join("notas").in(nota));
			conditions.add(notaEqual);
		}

		if (!dataParteDiariaMin.equals("") && !dataParteDiariaMin.equals("__/__/____")) {
			LocalDate aux = LocalDate.parse(dataParteDiariaMin, localDateFormat);
			Path<LocalDate> attrDataParteDiariaMin = root.get("dataParteDiaria");
			Predicate whereDataParteDiaria = criteriaBuilder.greaterThanOrEqualTo(attrDataParteDiariaMin, aux);
			conditions.add(whereDataParteDiaria);
		}

		if (!dataParteDiariaMax.equals("") && !dataParteDiariaMax.equals("__/__/____")) {
			LocalDate aux = LocalDate.parse(dataParteDiariaMax, localDateFormat);
			Path<LocalDate> attrDataParteDiariaMax = root.get("dataParteDiaria");
			Predicate whereDataParteDiaria = criteriaBuilder.lessThanOrEqualTo(attrDataParteDiariaMax, aux);
			conditions.add(whereDataParteDiaria);
		}

		Predicate[] conditionsToArray = conditions.toArray(new Predicate[conditions.size()]);

		Predicate allConditions = criteriaBuilder.and(conditionsToArray);

		criteriaQuery.where(allConditions);

		TypedQuery<ParteDiaria> query = manager.createQuery(criteriaQuery);

		Integer resultCont = query.getResultList().size();

		query.setFirstResult(first);
		query.setMaxResults(rows);

		FilterDTO manutencaoDTO = new FilterDTO(resultCont, query.getResultList());

		return manutencaoDTO;
	}

}
