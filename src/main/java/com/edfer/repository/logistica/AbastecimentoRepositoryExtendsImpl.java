package com.edfer.repository.logistica;

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

import org.springframework.stereotype.Repository;

import com.edfer.model.enuns.TipoCombustivel;
import com.edfer.model.filtersDTO.FilterDTO;
import com.edfer.model.logistica.Abastecimento;
import com.edfer.model.logistica.Posto;
import com.edfer.model.logistica.Veiculo;

@Repository
public class AbastecimentoRepositoryExtendsImpl implements AbastecimentoRepositoryExtends {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Abastecimento> findAllLimitOrderByDate(Long idVeiculo) {
		String query = "SELECT NEW Abastecimento(a.idAbastecimento, a.dataAbastecimento, a.posto, a.hodometro,\r\n"
				+ "			a.quantidadeLitro, a.valorLitro, a.valorTotal) FROM Abastecimento a INNER JOIN a.veiculo v WHERE v.idVeiculo = ?1 ORDER BY a.dataAbastecimento DESC";

		List<Abastecimento> result = manager.createQuery(query, Abastecimento.class).setParameter(1, idVeiculo)
				.setMaxResults(100).getResultList();

		return result;
	}

	@Override
	public FilterDTO findByCriteria(int rows, int first, String sortField, String sortOrder, String idAbastecimento,
			String veiculos, String abastecimentoDateMin, String abastecimentoDateMax, String postos, String tipoCombustivel) {

		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Abastecimento> criteriaQuery = criteriaBuilder.createQuery(Abastecimento.class);
		Root<Abastecimento> root = criteriaQuery.from(Abastecimento.class);
		criteriaQuery.distinct(true);

		DateTimeFormatter localDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter localDateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		Join<Abastecimento, Veiculo> veiculo = root.join("veiculo", JoinType.INNER);
		Join<Abastecimento, Posto> posto = root.join("posto", JoinType.INNER);

		if (!sortField.equals("")) {
			Path<?> attribOrder = root.get(sortField);
			if (sortOrder.equals("1")) {
				criteriaQuery.orderBy(criteriaBuilder.asc(attribOrder));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(attribOrder));
			}
		}

		List<Predicate> conditions = new ArrayList<Predicate>();

		if (!idAbastecimento.equals("")) {
			Long idAbastecimentoAux = Long.parseLong(idAbastecimento);
			Path<Long> attrIdAbastecimento = root.get("idAbastecimento");
			Predicate equalNumeroIdAbastecimento = criteriaBuilder.equal(attrIdAbastecimento, idAbastecimentoAux);
			conditions.add(equalNumeroIdAbastecimento);
		}

		if (!veiculos.equals("")) {
			List<Long> listVeiculos = Stream.of(veiculos.split(",")).map(Long::parseLong).collect(Collectors.toList());
			Path<Long> attrVeiculo = veiculo.get("idVeiculo");
			Predicate containsVeiculos = criteriaBuilder.isTrue(attrVeiculo.in(listVeiculos));
			conditions.add(containsVeiculos);
		}

		if (!abastecimentoDateMin.equals("") && !abastecimentoDateMin.equals("__/__/____ 00:00")) {
			LocalDateTime aux = LocalDateTime.parse(abastecimentoDateMin, localDateTimeFormat);
			Path<LocalDateTime> attrAbastecimentoMin = root.get("dataAbastecimento");
			Predicate whereDataAbastecimentoMin = criteriaBuilder.greaterThanOrEqualTo(attrAbastecimentoMin, aux);
			System.out.println(whereDataAbastecimentoMin);
			conditions.add(whereDataAbastecimentoMin);
		}

		if (!abastecimentoDateMax.equals("") && !abastecimentoDateMax.equals("__/__/____ 00:00")) {
			LocalDateTime aux = LocalDateTime.parse(abastecimentoDateMax, localDateTimeFormat);
			Path<LocalDateTime> attrAbastecimentoDateMax = root.get("dataAbastecimento");
			Predicate whereDataAbastecimentoMax = criteriaBuilder.lessThanOrEqualTo(attrAbastecimentoDateMax, aux);
			conditions.add(whereDataAbastecimentoMax);
		}

		if (!postos.equals("")) {
			List<Long> listPostos = Stream.of(postos.split(",")).map(Long::parseLong).collect(Collectors.toList());
			Path<Long> attrPostos = posto.get("idPosto");
			Predicate containsPostos = criteriaBuilder.isTrue(attrPostos.in(listPostos));
			conditions.add(containsPostos);
		}
		
		if (!tipoCombustivel.equals("")) {
			List<TipoCombustivel> listTipoCombustivel = Stream.of(tipoCombustivel.split(",")).map(TipoCombustivel::valueOf).collect(Collectors.toList());
			Path<TipoCombustivel> attrTipoCombustivel = root.get("tipoCombustivel");
			Predicate containsTipoCombustivel = criteriaBuilder.isTrue(attrTipoCombustivel.in(listTipoCombustivel));
			conditions.add(containsTipoCombustivel);
		}

		Predicate[] conditionsToArray = conditions.toArray(new Predicate[conditions.size()]);

		Predicate allConditions = criteriaBuilder.and(conditionsToArray);

		criteriaQuery.where(allConditions);

		TypedQuery<Abastecimento> query = manager.createQuery(criteriaQuery);

		Integer resultCont = query.getResultList().size();

		query.setFirstResult(first);
		query.setMaxResults(rows);

		FilterDTO manutencaoDTO = new FilterDTO(resultCont, query.getResultList());

		return manutencaoDTO;
	}

}
