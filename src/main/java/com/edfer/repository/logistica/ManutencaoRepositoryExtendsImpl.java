package com.edfer.repository.logistica;

import java.math.BigDecimal;
import java.text.Normalizer;
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

import org.springframework.stereotype.Repository;

import com.edfer.model.enuns.TipoManutencao;
import com.edfer.model.enuns.TipoNota;
import com.edfer.model.enuns.TipoServico;
import com.edfer.model.filtersDTO.FilterDTO;
import com.edfer.model.logistica.Manutencao;
import com.edfer.model.logistica.Veiculo;

@Repository
public class ManutencaoRepositoryExtendsImpl implements ManutencaoRepositoryExtends {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public FilterDTO findAllByCriteria(String sortField, String sortOrder, int first, int rows, String idManutencao,
			String tipoManutencao, String tipoServico, String valorMin, String valorMax, String tipoNota,
			String hodometroMin, String hodometroMax, String manutencaoDateMin, String manutencaoDateMax,
			String fornecedor, String numNotaOuNumOrdem, String veiculos) {
		
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Manutencao> criteriaQuery = criteriaBuilder.createQuery(Manutencao.class);
		Root<Manutencao> root = criteriaQuery.from(Manutencao.class);
		criteriaQuery.distinct(true);

		DateTimeFormatter localDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter localDateTimeFormat =
		DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		Join<Manutencao, Veiculo> veiculo = root.join("veiculo", JoinType.INNER);

		if (!sortField.equals("")) {
			Path<?> attribOrder = root.get(sortField);
			if (sortOrder.equals("1")) {
				criteriaQuery.orderBy(criteriaBuilder.asc(attribOrder));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(attribOrder));
			}
		}

		List<Predicate> conditions = new ArrayList<Predicate>();

		if (!idManutencao.equals("")) {
			Long idManutencaoAux = Long.parseLong(idManutencao);
			Path<Long> attrIdManutencao = root.get("idManutencao");
			Predicate equalNumeroIdManutencao = criteriaBuilder.equal(attrIdManutencao, idManutencaoAux);
			conditions.add(equalNumeroIdManutencao);
		}

		if (!veiculos.equals("")) {
			List<Long> listVeiculos = Stream.of(veiculos.split(",")).map(Long::parseLong).collect(Collectors.toList());
			Path<Long> attrVeiculo = veiculo.get("idVeiculo");
			Predicate containsVeiculos = criteriaBuilder.isTrue(attrVeiculo.in(listVeiculos));
			conditions.add(containsVeiculos);
		}

		if (!tipoManutencao.equals("")) {
			List<TipoManutencao> listTipoManutencao = Stream.of(tipoManutencao.split(",")).map(TipoManutencao::valueOf)
					.collect(Collectors.toList());
			Path<TipoManutencao> attrTipoManutencao = root.get("tipoManutencao");
			Predicate containsTipoManutencao = criteriaBuilder.isTrue(attrTipoManutencao.in(listTipoManutencao));
			conditions.add(containsTipoManutencao);
		}

		if (!tipoServico.equals("")) {
			System.out.println(tipoServico);
			List<String> listTipoServico = Stream.of(tipoServico.replace(" ", "_").split(",")).collect(Collectors.toList());
			List<TipoServico> list = new ArrayList<>();
			for(String tipo : listTipoServico) {
				tipo = Normalizer.normalize(tipo, Normalizer.Form.NFD);
				tipo = tipo.replaceAll("[^\\p{ASCII}]", "");
				list.add(TipoServico.valueOf(tipo.toUpperCase()));
			}
			Predicate tipoService = criteriaBuilder.and(root.join("tipoServico").in(list));
			conditions.add(tipoService);
		}

		if (!tipoNota.equals("")) {
			List<TipoNota> listTipoNota = Stream.of(tipoNota.split(",")).map(TipoNota::valueOf)
					.collect(Collectors.toList());
			System.out.println(listTipoNota);
			Path<TipoNota> attrTipoNota = root.get("tipoNota");
			Predicate containsTipoNota = criteriaBuilder.isTrue(attrTipoNota.in(listTipoNota));
			conditions.add(containsTipoNota);
		}

		if (!manutencaoDateMin.equals("") && !manutencaoDateMin.equals("__/__/____")) {
			LocalDate manutencaoDateMinAux = LocalDate.parse(manutencaoDateMin, localDateFormat);
			Path<LocalDate> attManutencaoDate = root.get("dataManutencao");
			Predicate whereDataManutencaoMin = criteriaBuilder.greaterThanOrEqualTo(attManutencaoDate,
					manutencaoDateMinAux);
			conditions.add(whereDataManutencaoMin);
		}

		if (!manutencaoDateMax.equals("") && !manutencaoDateMax.equals("__/__/____")) {
			LocalDate manutencaoDateMaxAux = LocalDate.parse(manutencaoDateMax, localDateFormat);
			Path<LocalDate> attManutencaoDate = root.get("dataManutencao");
			Predicate whereDataManutencaoMax = criteriaBuilder.greaterThanOrEqualTo(attManutencaoDate,
					manutencaoDateMaxAux);
			conditions.add(whereDataManutencaoMax);
		}

		if (!valorMin.equals("")) {
			BigDecimal valorAux = BigDecimal.valueOf(Double.parseDouble(valorMin));
			Path<BigDecimal> attrValorMin = root.get("valorManutencao");
			Predicate gtValueMin = criteriaBuilder.lessThanOrEqualTo(attrValorMin, valorAux);
			conditions.add(gtValueMin);
		}

		if (!valorMax.equals("")) {
			BigDecimal valorAux = BigDecimal.valueOf(Double.parseDouble(valorMax));
			Path<BigDecimal> attrValorMax = root.get("valorManutencao");
			Predicate ltValueMax = criteriaBuilder.greaterThanOrEqualTo(attrValorMax, valorAux);
			conditions.add(ltValueMax);
		}

		Predicate[] conditionsToArray = conditions.toArray(new Predicate[conditions.size()]);

		Predicate allConditions = criteriaBuilder.and(conditionsToArray);

		criteriaQuery.where(allConditions);

		TypedQuery<Manutencao> query = manager.createQuery(criteriaQuery);

		Integer resultCont = query.getResultList().size();

		query.setFirstResult(first);
		query.setMaxResults(rows);

		FilterDTO manutencaoDTO = new FilterDTO(resultCont, query.getResultList());

		return manutencaoDTO;
	}
}
