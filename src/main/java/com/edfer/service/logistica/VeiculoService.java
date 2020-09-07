package com.edfer.service.logistica;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.edfer.model.Categoria;
import com.edfer.model.logistica.FabricanteVeiculo;
import com.edfer.model.logistica.Veiculo;

@Service
public interface VeiculoService {

	void salvar(Veiculo veiculo);

	void delete(Long idVeiculo);

	void update(Veiculo veiculo);

	Optional<Veiculo> findById(Long idVeiculo);

	List<Veiculo> findAll();

	Optional<List<Veiculo>> findByCategoria(Categoria categoria);

	Optional<List<Veiculo>> findByFabricante(FabricanteVeiculo fabricante);

	Optional<Veiculo> findByPlaca(String placa);

	Optional<List<Veiculo>> findByModelo(String modelo);

	List<Veiculo> findAllFilterInit();

	void updateAtividadeVeiculo(boolean ativo, Long idVeiculo);

	Veiculo getNumeroDeEixos(Long idVeiculo);

	void sumHodometro(Long idVeiculo, Long hodometro);
	
	void updateHodometroViaAbastecimento(Long idVeiculo, Long hodometro);
}
