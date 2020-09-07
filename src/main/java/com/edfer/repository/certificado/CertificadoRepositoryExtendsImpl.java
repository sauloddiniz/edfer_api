package com.edfer.repository.certificado;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

import com.edfer.model.certificado.Certificado;
import com.edfer.model.certificado.ProdutoCertificado;
import com.edfer.model.certificado.ProdutoCertificadoAux;
import com.edfer.model.filtersDTO.FilterDTO;

public class CertificadoRepositoryExtendsImpl implements CertificadoRepositoryExtends {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public FilterDTO findCertificadoByCriteria(String first, String rows, String sortField, String sortOrder,
			String codigo, String dateMin, String dateMax, String numero, String fornecedor, String norma,
			String corrida, String volume, String dimensao) {

		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Certificado> criteriaQuery = criteriaBuilder.createQuery(Certificado.class);
		Root<Certificado> root = criteriaQuery.from(Certificado.class);
		criteriaQuery.distinct(true);

		DateTimeFormatter localDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		if (!sortField.equals("")) {
			Path<?> attribOrder = root.get(sortField);
			if (sortOrder.equals("1")) {
				criteriaQuery.orderBy(criteriaBuilder.asc(attribOrder));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(attribOrder));
			}
		}

		Join<Certificado, ProdutoCertificado> produto = root.join("produtos", JoinType.INNER);
		Join<Certificado, ProdutoCertificadoAux> produtoCertificadoAux = root.join("produtoCertificadoAux", JoinType.INNER);
		
		List<Predicate> conditions = new ArrayList<Predicate>();


		if (!dateMin.equals("") && !dateMin.equals("__/__/____")) {
			LocalDate dateMinAux = LocalDate.parse(dateMin, localDateFormat);
			Path<LocalDate> attributDateMin = root.get("dataEmissao");
			Predicate whereDateMin = criteriaBuilder.greaterThanOrEqualTo(attributDateMin, dateMinAux);
			conditions.add(whereDateMin);
		}

		if (!dateMax.equals("") && !dateMax.equals("__/__/____")) {
			LocalDate dateMaxAux = LocalDate.parse(dateMax, localDateFormat);
			Path<LocalDate> attributDateMax = root.get("dataEmissao");
			Predicate whereDateMax = criteriaBuilder.lessThanOrEqualTo(attributDateMax, dateMaxAux);
			conditions.add(whereDateMax);
		}

		if (codigo != "") {
			Path<String> attrCodigo = produto.get("codigo");
			Predicate whereCodigoo = criteriaBuilder.like(attrCodigo, "%" + codigo + "%");
			conditions.add(whereCodigoo);
		}
		
		if (numero != "") {
			Path<String> attrNumero = root.get("numero");
			Predicate whereNumero = criteriaBuilder.like(attrNumero, "%" + numero + "%");
			conditions.add(whereNumero);
		}

		if (fornecedor != "") {
			Path<String> attrFornecedor = root.get("fornecedor");
			Predicate whereFornecedor = criteriaBuilder.like(attrFornecedor, "%" + fornecedor + "%");
			conditions.add(whereFornecedor);
		}
		
		if (norma != "") {
			Path<String> attrNorma = root.get("norma");
			Predicate whereNorma = criteriaBuilder.like(attrNorma, "%" + norma + "%");
			conditions.add(whereNorma);
		}

		if (!corrida.equals("")) {
			Path<String> attrCorrida = produtoCertificadoAux.get("corrida");
			Predicate whereCorrida = criteriaBuilder.like(attrCorrida, "%" + corrida + "%");
			conditions.add(whereCorrida);
		}

		if (!volume.equals("")) {
			Path<String> attrVolume = produtoCertificadoAux.get("volume");
			Predicate whereVolume = criteriaBuilder.like(attrVolume, "%" + volume + "%");
			conditions.add(whereVolume);;
		}
		
		if (!dimensao.equals("")) {
			Path<String> attrDimensao = root.get("dimensao");
			Predicate whereDimensao = criteriaBuilder.like(attrDimensao, "%" + dimensao + "%");
			conditions.add(whereDimensao);;
		}

		Predicate[] conditionsToArray = conditions.toArray(new Predicate[conditions.size()]);

		Predicate allConditions = criteriaBuilder.and(conditionsToArray);

		criteriaQuery.where(allConditions);

		TypedQuery<Certificado> query = manager.createQuery(criteriaQuery);
		
		Integer resultCont = query.getResultList().size();

		query.setFirstResult(Integer.valueOf(first));
		query.setMaxResults(Integer.valueOf(rows));

		FilterDTO filterDTO = new FilterDTO(resultCont, query.getResultList());

		return filterDTO;
	}

}
