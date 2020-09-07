package com.edfer.service.almoxerifado;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.almoxerifado.EntradaSaidaMiudeza;
import com.edfer.model.almoxerifado.Miudeza;
import com.edfer.model.almoxerifado.enuns.EnumEntradaSaida;
import com.edfer.repository.almoxerifado.EntradaSaidaRepository;
import com.edfer.repository.almoxerifado.MiudezaRepository;

@Service
@Transactional
public class EntradaSaidaServiceImpl implements EntradaSaidaService {

	@Autowired
	private EntradaSaidaRepository repository;

	@Autowired
	private MiudezaRepository miudezaRepository;

	@Override
	public void save(EntradaSaidaMiudeza entradaSaidaMiudeza) {

		Optional<Miudeza> miudeza = miudezaRepository.findById(entradaSaidaMiudeza.getMiudeza().getIdMiudeza());
		if(miudeza.isPresent()) {
			miudeza.get().addEntradaSaida(entradaSaidaMiudeza);
			if (EnumEntradaSaida.valueOf(entradaSaidaMiudeza.getEntradaSaida().toString()) == EnumEntradaSaida.COMPRA) {
				miudeza.get().sumEstoque(entradaSaidaMiudeza.getQuantidade());
			} else if (EnumEntradaSaida
					.valueOf(entradaSaidaMiudeza.getEntradaSaida().toString()) == EnumEntradaSaida.VENDA) {
				miudeza.get().subEstoque(entradaSaidaMiudeza.getQuantidade());
			}
			repository.save(entradaSaidaMiudeza);
			miudezaRepository.save(miudeza.get());
		}
	}

	@Override
	public List<EntradaSaidaMiudeza> findAll() {
		return repository.findAllOrderByIdEntradaSaida();
	}

	@Override
	public void update(Long idEntradaSaida) {
		EntradaSaidaMiudeza entradaSaidaMiudeza = repository.getOne(idEntradaSaida);
		if (entradaSaidaMiudeza.getEntradaSaida() == EnumEntradaSaida.VENDA) {
			
			Miudeza miudeza = entradaSaidaMiudeza.getMiudeza();
			miudeza.sumEstoque(entradaSaidaMiudeza.getQuantidade());
			entradaSaidaMiudeza.setEntradaSaida(EnumEntradaSaida.CANCELADO);
			
			miudezaRepository.save(miudeza);
			repository.save(entradaSaidaMiudeza);
			
		} else if (entradaSaidaMiudeza.getEntradaSaida() == EnumEntradaSaida.COMPRA) {
			Miudeza miudeza = entradaSaidaMiudeza.getMiudeza();
			miudeza.subEstoque(entradaSaidaMiudeza.getQuantidade());
			entradaSaidaMiudeza.setEntradaSaida(EnumEntradaSaida.CANCELADO);
			
			miudezaRepository.save(miudeza);
			repository.save(entradaSaidaMiudeza);
		}
	}

}
