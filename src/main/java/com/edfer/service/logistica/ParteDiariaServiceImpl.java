package com.edfer.service.logistica;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.enuns.Posicao;
import com.edfer.model.filtersDTO.FilterDTO;
import com.edfer.model.logistica.GastosDiaria;
import com.edfer.model.logistica.ParteDiaria;
import com.edfer.model.logistica.Pneu;
import com.edfer.model.logistica.RotaDiaria;
import com.edfer.model.logistica.Veiculo;
import com.edfer.repository.logistica.GastosDiariaRepository;
import com.edfer.repository.logistica.ParteDiariaRepository;
import com.edfer.repository.logistica.RotaDiariaRepository;

@Service
@Transactional
public class ParteDiariaServiceImpl implements ParteDiariaService {

	@Autowired
	private ParteDiariaRepository repository;

	@Autowired
	private VeiculoService veiculoService;

	@Autowired
	private PneuService pneuService;

	@Autowired
	private RotaDiariaRepository rotaRepository;

	@Autowired
	private GastosDiariaRepository gastoRepository;

	@Override
	public void salvar(ParteDiaria parteDiaria) {

		List<RotaDiaria> rotas = parteDiaria.getRotaDiaria();
		List<GastosDiaria> gastos = parteDiaria.getGastos();

		Duration totalTimeValid = Duration.ZERO;
		Duration totalTime = Duration.ZERO;
		Long kmInicial = Long.MAX_VALUE;
		Long kmFinal = 0L;
		for (RotaDiaria rota : rotas) {
			totalTimeValid = totalTimeValid.plus(rota.cargaDescargaEdfer());
			totalTimeValid = totalTimeValid.plus(rota.itinerarioClienteOuFornecedor());
			totalTimeValid = totalTimeValid.plus(rota.cargaDescargaClienteFornecedor());
			totalTimeValid = totalTimeValid.plus(rota.itinerarioEdfer());
			rota.disposicaoCliente();
			totalTime = totalTime.plus(rota.totalTime());

			if (kmInicial >= rota.getKmInicial()) {
				kmInicial = rota.getKmInicial();
			}

			if (kmFinal <= rota.getKmFinal()) {
				kmFinal = rota.getKmFinal();
			}
		}

		parteDiaria.setHsTotalParteDiaria(totalTime);
		parteDiaria.setHsTotalValida(totalTimeValid);
		parteDiaria.setKmRodado(kmFinal - kmInicial);

		updateVeiculo(parteDiaria.getVeiculo(), parteDiaria.getKmRodado());
		updatePneus(parteDiaria.getVeiculo(), parteDiaria.getKmRodado());

		repository.save(parteDiaria);

		if (!rotas.isEmpty()) {
			rotas.parallelStream().forEach(parteDiaria::addRotaDiaria);
		}

		if (!gastos.isEmpty()) {
			gastos.parallelStream().forEach(parteDiaria::addGastoDiaria);
		}
	}

	@Override
	public void delete(Long idParteDiaria) {
		repository.deleteById(idParteDiaria);
	}

	@Override
	public void update(Long idParteDiaria, ParteDiaria parteDiaria) {
		repository.save(parteDiaria);
	}

	@Override
	@Transactional(readOnly = true)
	public ParteDiaria findByIdParteDiaria(Long idParteDiaria) {
		return repository.findById(idParteDiaria).get();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ParteDiaria> findAll() {
		return repository.findAll();
	}

	@Override
	public FilterDTO findByCriteria(String rows, String first, String sortField, String sortOrder, String veiculos,
			String dataParteDiariaMin, String dataParteDiariaMax, String cliente, String nota) {

		return repository.findByCriteria(Integer.parseInt(rows), Integer.parseInt(first), sortField, sortOrder,
				veiculos, dataParteDiariaMin, dataParteDiariaMax, cliente, nota);
	}

	@Override
	public Optional<ParteDiaria> findByDataAndVeiculo(LocalDate dataParteDiaira, Long idVeiculo, Long idFuncionario) {
		return repository.findByDataParteDiariaAndVeiculoIdVeiculoAndMotoristaIdFuncionario(dataParteDiaira, idVeiculo,
				idFuncionario);
	}

	@Override
	public void updateParteDiaria(ParteDiaria parteDiaria) {

		Duration totalTimeValid = Duration.ZERO;
		Duration totalTime = Duration.ZERO;
		Long kmInicial = Long.MAX_VALUE;
		Long kmFinal = 0L;

		if (!parteDiaria.getGastos().isEmpty()) {
			for (GastosDiaria gasto : parteDiaria.getGastos()) {
				gasto.setParteDiaria(parteDiaria);
				gastoRepository.save(gasto);
			}
		}

		for (RotaDiaria rota : parteDiaria.getRotaDiaria()) {
			totalTimeValid = totalTimeValid.plus(rota.cargaDescargaEdfer());
			totalTimeValid = totalTimeValid.plus(rota.itinerarioClienteOuFornecedor());
			totalTimeValid = totalTimeValid.plus(rota.cargaDescargaClienteFornecedor());
			totalTimeValid = totalTimeValid.plus(rota.itinerarioEdfer());
			rota.disposicaoCliente();
			totalTime = totalTime.plus(rota.totalTime());

			if (kmInicial >= rota.getKmInicial()) {
				kmInicial = rota.getKmInicial();
			}

			if (kmFinal <= rota.getKmFinal()) {
				kmFinal = rota.getKmFinal();
			}
			rota.setParteDiaria(parteDiaria);
			rotaRepository.save(rota);
		}

		parteDiaria.setHsTotalParteDiaria(totalTime);
		parteDiaria.setHsTotalValida(totalTimeValid);
		parteDiaria.setKmRodado(kmFinal - kmInicial);

		Optional<Long> km = repository.kmParteDiaria(parteDiaria.getIdParteDiaria());

		// UPDATE Hodometro veiculo e KM Pneu
		if (!((km.get()).equals(parteDiaria.getKmRodado()))) {
			updateVeiculo(parteDiaria.getVeiculo(), (parteDiaria.getKmRodado() - (km.get())));
			updatePneus(parteDiaria.getVeiculo(), (parteDiaria.getKmRodado() - (km.get())));
		}

		repository.saveAndFlush(parteDiaria);
	}

	private void updateVeiculo(Veiculo veiculo, Long kmRodado) {
		veiculoService.sumHodometro(veiculo.getIdVeiculo(), kmRodado);
	}

	private void updatePneus(Veiculo veiculo, Long kmRodado) {
		Optional<List<Pneu>> pneus = pneuService.findAllByVeiculo(veiculo.getIdVeiculo());

		if (pneus.isPresent()) {
			for (Pneu pneu : pneus.get()) {
				if (pneu.getPosicao() != Posicao.ESTEPE) {
					pneu.sumKm(kmRodado);
					pneuService.update(pneu);
				}
			}
		}
	}
}
