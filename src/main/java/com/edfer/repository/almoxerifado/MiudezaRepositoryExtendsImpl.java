package com.edfer.repository.almoxerifado;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.edfer.model.almoxerifado.Miudeza;
import com.edfer.model.filtersDTO.FilterDTO;

public class MiudezaRepositoryExtendsImpl implements MiudezaRepositoryExtends{

	@PersistenceContext
	private EntityManager manager;

	@Override
	public FilterDTO findByCriteria(String first, String rows, String sortField, String sortOrder, String codigo, String descricao) {
		
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Miudeza> criteriaQuery = criteriaBuilder.createQuery(Miudeza.class);
		Root<Miudeza> root = criteriaQuery.from(Miudeza.class);
		criteriaQuery.distinct(true);

		if (!sortField.equals("")) {
			Path<?> attribOrder = root.get(sortField);
			if (sortOrder.equals("1")) {
				criteriaQuery.orderBy(criteriaBuilder.asc(attribOrder));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(attribOrder));
			}
		}

		List<Predicate> conditions = new ArrayList<Predicate>();

		if (codigo != "") {
			Path<String> attrCodigo = root.get("codigo");
			Predicate whereCodigoo = criteriaBuilder.like(attrCodigo, codigo + "%");
			conditions.add(whereCodigoo);
		}
		
		if (descricao != "") {
			Path<String> attrDescricao = root.get("descricao");
			Predicate whereDescricao = criteriaBuilder.like(attrDescricao, "%" + descricao + "%");
			conditions.add(whereDescricao);
		}

		Predicate[] conditionsToArray = conditions.toArray(new Predicate[conditions.size()]);

		Predicate allConditions = criteriaBuilder.and(conditionsToArray);

		criteriaQuery.where(allConditions);

		TypedQuery<Miudeza> query = manager.createQuery(criteriaQuery);
		
		Integer resultCont = query.getResultList().size();

		query.setFirstResult(Integer.valueOf(first));
		query.setMaxResults(Integer.valueOf(rows));

		FilterDTO filterDTO = new FilterDTO(resultCont, query.getResultList());

		return filterDTO;

	}
}
