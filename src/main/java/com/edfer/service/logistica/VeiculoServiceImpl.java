package com.edfer.service.logistica;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.Categoria;
import com.edfer.model.logistica.FabricanteVeiculo;
import com.edfer.model.logistica.Veiculo;
import com.edfer.repository.logistica.VeiculoRepository;

@Service
public class VeiculoServiceImpl implements VeiculoService {

	@Autowired
	private VeiculoRepository repository;

	@Override
	public void salvar(Veiculo veiculo) {
		repository.save(veiculo);
	}

	@Override
	public void delete(Long idVeiculo) {
		repository.deleteById(idVeiculo);
	}

	@Override
	public void update(Veiculo veiculo) {
		repository.save(veiculo);
	}

	@Override
	public Optional<Veiculo> findById(Long idVeiculo) {
		return repository.findById(idVeiculo);
	}

	@Override
	public List<Veiculo> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<List<Veiculo>> findByCategoria(Categoria categoria) {
		return repository.findByCategoria(categoria);
	}

	@Override
	public Optional<List<Veiculo>> findByFabricante(FabricanteVeiculo fabricante) {
		return repository.findByFabricante(fabricante);
	}

	@Override
	public Optional<Veiculo> findByPlaca(String placa) {
		return repository.findByPlaca(placa);
	}

	@Override
	public Optional<List<Veiculo>> findByModelo(String modelo) {
		return repository.findByModelo(modelo);
	}

	@Override
	public List<Veiculo> findAllFilterInit() {
		return repository.findAllFilterInit();
	}

	@Override
	public void updateAtividadeVeiculo(boolean ativo, Long idVeiculo) {
		repository.updateAtividadeVeiculo(ativo, idVeiculo);
	}

	@Override
	@Transactional(readOnly = true)
	public Veiculo getNumeroDeEixos(Long idVeiculo) {
		return repository.selectNumeroTotalDeEixos(idVeiculo);
	}

	@Override
	public void sumHodometro(Long idVeiculo, Long hodometro) {
		Veiculo veiculo = repository.getOne(idVeiculo);
		System.out.println("ANTES" + veiculo.getHodometro());
		veiculo.setHodometro(veiculo.getHodometro() + hodometro);
		System.out.println("DEPOIS" + veiculo.getHodometro());
		update(veiculo);
	}

	@Override
	public void updateHodometroViaAbastecimento(Long idVeiculo, Long hodometro) {
		if(repository.findHodometroByVeiculo(idVeiculo).get() <= hodometro) {
			repository.updateHodometro(idVeiculo, hodometro);			
		}
	}
}
