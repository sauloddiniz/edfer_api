package com.edfer.repository.logistica;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.logistica.RotaDiaria;

@Repository
public interface RotaDiariaRepository extends JpaRepository<RotaDiaria, Long>{

	@Transactional(readOnly = true)
	@Query(value="SELECT r.nomeClienteOuFornecedor FROM RotaDiaria r WHERE r.nomeClienteOuFornecedor LIKE :nomeClienteOuFornecedor% GROUP BY r.nomeClienteOuFornecedor")
	List<String> findByNomeClienteOuFornecedor(String nomeClienteOuFornecedor);

}
