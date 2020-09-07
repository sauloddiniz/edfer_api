package com.edfer.repository.logistica;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.Categoria;
import com.edfer.model.logistica.FabricanteVeiculo;
import com.edfer.model.logistica.Veiculo;

@Repository
@Transactional
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

	@Query("SELECT NEW Veiculo(v.idVeiculo, v.placa, v.modelo) FROM Veiculo v")
	List<Veiculo> findAllFilterInit();

	@Query("SELECT NEW Veiculo(v.numTotalDeEixos, v.categoria) FROM Veiculo v WHERE v.idVeiculo = :idVeiculo")
	Veiculo selectNumeroTotalDeEixos(Long idVeiculo);

	@Modifying
	@Query("UPDATE Veiculo v SET v.ativo = :ativo WHERE v.idVeiculo = :idVeiculo")
	void updateAtividadeVeiculo(boolean ativo, Long idVeiculo);

	@Modifying
	@Query("UPDATE Veiculo v SET v.hodometro = :hodometro WHERE v.idVeiculo = :idVeiculo")
	void updateHodometro(Long idVeiculo, Long hodometro);

	@Transactional(readOnly = true)
	Optional<List<Veiculo>> findByCategoria(Categoria categoria);

	Optional<List<Veiculo>> findByFabricante(FabricanteVeiculo fabricante);

	Optional<Veiculo> findByPlaca(String placa);

	Optional<List<Veiculo>> findByModelo(String modelo);

	@Transactional(readOnly = true)
	@Query(value = "SELECT v.hodometro FROM Veiculo v WHERE v.idVeiculo = :idVeiculo")
	Optional<Long> findHodometroByVeiculo(Long idVeiculo);
}
