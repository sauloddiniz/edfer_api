package com.edfer.repository.logistica;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.enuns.EnumAtivo;
import com.edfer.model.enuns.Estado;
import com.edfer.model.enuns.Posicao;
import com.edfer.model.filtersDTO.FilterDTO;
import com.edfer.model.logistica.EstadoPneu;
import com.edfer.model.logistica.FabricantePneu;
import com.edfer.model.logistica.Pneu;
import com.edfer.model.logistica.Veiculo;

@Repository
@Transactional
public class PneuRepositoryExtendsImpl implements PneuRepositoryExtends {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Pneu> findAllPneu() {

		String queryPneu = "SELECT p FROM Pneu p";
		List<Pneu> results = manager.createQuery(queryPneu, Pneu.class).getResultList();

		String query = "SELECT e FROM EstadoPneu e  INNER JOIN e.pneu p WHERE p.idPneu = :idPneu ORDER BY e.dataReforma DESC";
		for (Pneu pneu : results) {
			pneu.setEstadosPneu(manager.createQuery(query, EstadoPneu.class).setParameter("idPneu", pneu.getIdPneu())
					.setMaxResults(1).getResultList());
		}
		return results;
	}

	@Override
	public FilterDTO findAllByCriteria(String sortField, String sortOrder, int first, int rows, String veiculos,
			String codControleEdfer, String kmMin, String kmMax, String compraPneuDateMin, String compraPneuDateMax,
			String modelo, String fabricantePneu, String estadoAtual, String numSerie,
			String numNotaOuNumOrdem, String estepe, String ativo) {
		
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Pneu> criteriaQuery = criteriaBuilder.createQuery(Pneu.class);
		Root<Pneu> root = criteriaQuery.from(Pneu.class);
		criteriaQuery.distinct(true);

		DateTimeFormatter localDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		// DateTimeFormatter localDateTimeFormat =
		// DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		Join<Pneu, Veiculo> veiculo = root.join("veiculo", JoinType.LEFT);
		Join<Pneu, FabricantePneu> fabricante = root.join("fabricantePneu", JoinType.INNER);

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
			if (veiculos.equals("0")) {
				Path<Long> attrNullVeiculo = veiculo.get("idVeiculo");					
				Predicate veiculosNull = criteriaBuilder.isNull(attrNullVeiculo);
				conditions.add(veiculosNull);
			} else {
				List<Long> listVeiculos = Stream.of(veiculos.split(",")).map(Long::parseLong).collect(Collectors.toList());
				Path<Long> attrVeiculo = veiculo.get("idVeiculo");
				Predicate containsVeiculos = criteriaBuilder.isTrue(attrVeiculo.in(listVeiculos));
				conditions.add(containsVeiculos);				
			}
		}

				
		if (!modelo.equals("")) {
			Path<String> attrCaracteristicas = root.get("caracteristica");
			Predicate caracteristicasContains = criteriaBuilder.like(attrCaracteristicas, "%" + modelo + "%");
			conditions.add(caracteristicasContains);
		}
		
		if (!codControleEdfer.equals("")) {
			Path<String> attrCodigoControleEdfer = root.get("codControleEdfer");
			Predicate attrCodigoControle = criteriaBuilder.like(attrCodigoControleEdfer, codControleEdfer + "%");
			conditions.add(attrCodigoControle);
		}

		if (!kmMin.equals("")) {
			Path<Long> attrKmMinAux = root.get("km");
			Predicate gtKmMin = criteriaBuilder.greaterThanOrEqualTo(attrKmMinAux, Long.parseLong(kmMin));
			conditions.add(gtKmMin);
		}

		if (!kmMax.equals("")) {
			Path<Long> attrKmMaxAux = root.get("km");
			Predicate ltKmMax = criteriaBuilder.lessThanOrEqualTo(attrKmMaxAux, Long.parseLong(kmMax));
			conditions.add(ltKmMax);
		}

		if (!compraPneuDateMin.equals("") && !compraPneuDateMin.equals("__/__/____")) {
			LocalDate compraPneuDateMinAux = LocalDate.parse(compraPneuDateMin, localDateFormat);
			Path<LocalDate> attCompraPneuDateMin = root.get("dataCompra");
			Predicate gtDataCompra = criteriaBuilder.greaterThanOrEqualTo(attCompraPneuDateMin, compraPneuDateMinAux);
			conditions.add(gtDataCompra);
		}

		if (!compraPneuDateMax.equals("") && !compraPneuDateMax.equals("__/__/____")) {
			LocalDate compraPneuDateMaxAux = LocalDate.parse(compraPneuDateMax, localDateFormat);
			Path<LocalDate> attrCompraPneuDateMax = root.get("dataCompra");
			Predicate ltDataCompra = criteriaBuilder.lessThanOrEqualTo(attrCompraPneuDateMax, compraPneuDateMaxAux);
			conditions.add(ltDataCompra);
		}

		if (!fabricantePneu.equals("")) {
			List<Long> listFabricante = Stream.of(fabricantePneu.split(",")).map(Long::parseLong)
					.collect(Collectors.toList());
			Path<Long> attrFabricante = fabricante.get("idFabricante");
			Predicate inFabricante = criteriaBuilder.isTrue(attrFabricante.in(listFabricante));
			conditions.add(inFabricante);
		}

		if (!numSerie.equals("")) {
			Path<String> numSerieAux = root.get("numSerie");
			Predicate attrNumSerie = criteriaBuilder.like(numSerieAux, numSerie + "%");
			conditions.add(attrNumSerie);
		}

		if (!estadoAtual.equals("")) {
			List<Estado> listEstado = Stream.of(estadoAtual.split(",")).map(Estado::valueOf)
					.collect(Collectors.toList());
			Path<Estado> attrEstado = root.get("estadoAtual");
			Predicate containsEstadoAtual = criteriaBuilder.isTrue(attrEstado.in(listEstado));
			conditions.add(containsEstadoAtual);
		}

		if (!numNotaOuNumOrdem.equals("")) {
			Path<String> attTumNotaOuNumOrdem = root.get("numNotaOuNumOrdem");
			Predicate likNumNotaOuNumOrdem = criteriaBuilder.like(attTumNotaOuNumOrdem, numNotaOuNumOrdem + "%");
			conditions.add(likNumNotaOuNumOrdem);
		}

		if (estepe.equals("SIM")) {
			Path<Posicao> attrPosicao = root.get("posicao");
			Predicate posicaoEqualsEstepe = criteriaBuilder.equal(attrPosicao, Posicao.ESTEPE);
			conditions.add(posicaoEqualsEstepe);
		}

		if (estepe.equals("NAO")) {
			List<Posicao> pos = Arrays.asList(
					Posicao.DIANTEIRO, Posicao.DIREITO, Posicao.DIREITO_DENTRO, Posicao.DIREITO_FORA,
					Posicao.ESQUERDO, Posicao.ESQUERDO_DENTRO, Posicao.ESQUERDO_FORA, Posicao.TRAZEIRO);
			Path<Posicao> attrPosicao = root.get("posicao");
			Predicate containsEstadoAtual = criteriaBuilder.isTrue(attrPosicao.in(pos));
			conditions.add(containsEstadoAtual);
		}

		if (!ativo.equals("")) {
			EnumAtivo enumAtivo = EnumAtivo.valueOf(ativo);
			Path<EnumAtivo> attrAtivo = root.get("ativo");
			Predicate isAtivo = criteriaBuilder.equal(attrAtivo, enumAtivo);
			conditions.add(isAtivo);
		}

		Predicate[] conditionsToArray = conditions.toArray(new Predicate[conditions.size()]);

		Predicate allConditions = criteriaBuilder.and(conditionsToArray);

		criteriaQuery.where(allConditions);

		TypedQuery<Pneu> query = manager.createQuery(criteriaQuery);

		Integer resultCont = query.getResultList().size();

		query.setFirstResult(first);
		query.setMaxResults(rows);

		FilterDTO manutencaoDTO = new FilterDTO(resultCont, query.getResultList());

		return manutencaoDTO;
	}

}
