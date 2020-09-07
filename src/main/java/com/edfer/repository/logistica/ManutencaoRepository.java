package com.edfer.repository.logistica;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.logistica.Manutencao;

@Repository
@Transactional
public interface ManutencaoRepository extends JpaRepository<Manutencao, Long>, com.edfer.repository.logistica.ManutencaoRepositoryExtends{

	@Transactional(readOnly = true)
	List<Manutencao> findAllByVeiculo_IdVeiculo_OrderByDataManutencaoDesc(Long idVeiculo);
	
	@Transactional(readOnly = true)
	@Query(value="SELECT m.fornecedor FROM Manutencao m WHERE m.fornecedor LIKE :fornecedor% GROUP BY m.fornecedor")
	List<String> findAllFornecedor(String fornecedor);

	@Transactional(readOnly = true)
	@Query(value="SELECT m FROM Manutencao m INNER JOIN m.veiculo v WHERE (m.validadeHodometro - v.hodometro) <= 1000")
	List<Manutencao> findAllManutencaoByHodometro();

	@Transactional(readOnly = true)
	List<Manutencao> findByValidadeManutencaoIn(List<LocalDate> listDate);
	
	@Transactional(readOnly= true)
	List<Manutencao> findAllByValidadeManutencaoLessThanEqualAndAndValidadeManutencaoGreaterThanEqual(
			LocalDate plusDays, LocalDate dateNow);
}
