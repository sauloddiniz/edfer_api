package com.edfer.repository.extend;

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

import com.edfer.model.Produto;
import com.edfer.model.filtersDTO.FilterDTO;

public class ProdutoRepositoryExtendsImpl implements ProdutoRepositoryExtends {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public FilterDTO findAllFilterCriteria(int first, int rows, String codigo, String descricao, String sortField, String sortOrder) {

		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		if (!sortField.equals("")) {
			Path<?> attribOrder = root.get(sortField);
			if (sortOrder.equals("1")) {
				criteriaQuery.orderBy(criteriaBuilder.asc(attribOrder));				
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(attribOrder));				
			}
		}

		List<Predicate> conditions = new ArrayList<Predicate>();

		if (!codigo.equals("")) {
			Path<String> attributCodigo = root.get("codigo");
			Predicate whereCodigo = criteriaBuilder.like(attributCodigo, codigo);
			conditions.add(whereCodigo);
		}

		if (!descricao.equals("")) {
			System.out.println("Descricao");
			Path<String> attributDescriao = root.get("descricao");
			Predicate containsDescricao = criteriaBuilder.like(attributDescriao, "%" + descricao + "%");
			conditions.add(containsDescricao);
		}

		Predicate[] conditionsToArray = conditions.toArray(new Predicate[conditions.size()]);

		Predicate allConditions = criteriaBuilder.and(conditionsToArray);

		criteriaQuery.where(allConditions);

		TypedQuery<Produto> query = manager.createQuery(criteriaQuery);

		Integer resultCont = query.getResultList().size();

		query.setFirstResult(first);
		query.setMaxResults(rows);

		FilterDTO filterDTO = new FilterDTO(resultCont, query.getResultList());

		return filterDTO;
	}

}
