package com.edfer.repository.certificado;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.edfer.model.certificado.ClienteCertificado;

@Repository
public interface ClienteCertificadoRepository extends JpaRepository<ClienteCertificado, Long> {

	List<ClienteCertificado> findAllByNumPedidoOrderByDataDesc(String numPedido);

	List<ClienteCertificado> findAllByProdutoCertificadoAux_Certificado_IdCertificado(Long idCertificado);

	@Query(value = "SELECT c.nome FROM ClienteCertificado c WHERE c.nome LIKE :nome% GROUP BY c.nome")
	List<String> findNome(String nome);
	
	List<ClienteCertificado> findAllByNomeEqualsOrDataGreaterThanEqual(String nome, LocalDate dateMin); 

}
