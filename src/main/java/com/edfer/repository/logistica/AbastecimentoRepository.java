package com.edfer.repository.logistica;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edfer.model.logistica.Abastecimento;

public interface AbastecimentoRepository extends JpaRepository<Abastecimento, Long>, AbastecimentoRepositoryExtends{

	Optional<Abastecimento> findFirstByVeiculoIdVeiculoOrderByIdAbastecimentoDesc(Long idVeiculo);

	List<Abastecimento> findFirst30ByVeiculoIdVeiculoOrderByDataAbastecimentoDesc(Long idVeiculo);

	Optional<Abastecimento> findTop1ByVeiculoIdVeiculoAndDataAbastecimentoLessThanOrderByDataAbastecimentoDesc(Long idVeiculo, LocalDateTime dataAbastecimento);

	Optional<Abastecimento> findTop1ByVeiculoIdVeiculoAndDataAbastecimentoGreaterThanOrderByDataAbastecimento(Long idVeiculo, LocalDateTime dataAbastecimento);
	
	@Query(value = "SELECT hodometro FROM edfer_logistica.abastecimento where id_veiculo = :idVeiculo AND hodometro < :hodometro ORDER BY hodometro DESC LIMIT 1", nativeQuery = true)
	Optional<Long> findMenorHodometro(Long idVeiculo, Long hodometro);
}
