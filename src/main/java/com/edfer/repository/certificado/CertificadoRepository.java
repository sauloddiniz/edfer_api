package com.edfer.repository.certificado;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.certificado.Certificado;

@Repository
public interface CertificadoRepository extends JpaRepository<Certificado, Long>, CertificadoRepositoryExtends {

	@Transactional(readOnly = true)
	@Query(value = "SELECT c.fornecedor FROM Certificado c WHERE c.fornecedor LIKE :fornecedor% GROUP BY c.fornecedor")
	List<String> findFornecedor(String fornecedor);

	@Transactional(readOnly = true)
	@Query(value = "SELECT c.norma FROM Certificado c WHERE c.norma LIKE :norma% GROUP BY c.norma")
	List<String> findNorma(String norma);

	@Transactional(readOnly = true)
	@Query(value = "SELECT DISTINCT c FROM Certificado c INNER JOIN c.produtos p WHERE p.idProdutoCertificado = :idProdutoCertificado ORDER BY c.dataEmissao DESC")
	List<Certificado> findAllByProdutos_IdProdutoCertificado(Long idProdutoCertificado);

	@Transactional(readOnly = true)
	Optional<String> findByNumero(String numero);
}
