package com.edfer.repository.certificado;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edfer.model.certificado.ProdutoCertificado;

@Repository
public interface ProdutoCertificadoRepository extends JpaRepository<ProdutoCertificado, Long>, ProdutoCertificadoRepositoryExtends{

	List<ProdutoCertificado> findByCodigoStartingWith(Long codigo);
	
	Optional<ProdutoCertificado> findByCodigo(String codigo);
}
