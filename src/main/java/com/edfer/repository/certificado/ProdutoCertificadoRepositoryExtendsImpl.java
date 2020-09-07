package com.edfer.repository.certificado;

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

import com.edfer.model.certificado.ProdutoCertificado;
import com.edfer.model.filtersDTO.FilterDTO;

public class ProdutoCertificadoRepositoryExtendsImpl implements ProdutoCertificadoRepositoryExtends {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public FilterDTO findProdutoByCriteria(String first, String rows, String sortField, String sortOrder, 
			String codigo, String classe, String descricao) {

		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<ProdutoCertificado> criteriaQuery = criteriaBuilder.createQuery(ProdutoCertificado.class);
		Root<ProdutoCertificado> root = criteriaQuery.from(ProdutoCertificado.class);
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
			Predicate whereCodigo = criteriaBuilder.like(attrCodigo, "%"+codigo+"%") ;
			conditions.add(whereCodigo);
		}

		if (classe != "") {
			Path<String> attrClasse = root.get("classe");
			Predicate whereClasse = criteriaBuilder.like(attrClasse, "%"+classe+"%") ;
			conditions.add(whereClasse);
		}
		
		if (descricao != "") {
			Path<String> attrDescricao = root.get("descricao");
			Predicate whereDescricao = criteriaBuilder.like(attrDescricao, "%"+descricao+"%") ;
			conditions.add(whereDescricao);
		}

		Predicate[] conditionsToArray = conditions.toArray(new Predicate[conditions.size()]);

		Predicate allConditions = criteriaBuilder.and(conditionsToArray);

		criteriaQuery.where(allConditions);

		TypedQuery<ProdutoCertificado> query = manager.createQuery(criteriaQuery);

		Integer resultCont = query.getResultList().size();

		query.setFirstResult(Integer.valueOf(first));
		query.setMaxResults(Integer.valueOf(rows));

		FilterDTO filterDTO = new FilterDTO(resultCont, query.getResultList());

		return filterDTO;
	}

}
