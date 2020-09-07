package com.edfer.repository.certificado;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.edfer.model.certificado.ProdutoCertificadoAux;

@Repository
public interface ProdutoCertificadoAuxRepository extends JpaRepository<ProdutoCertificadoAux, Long> {

	@Query(value="SELECT DISTINCT p FROM ProdutoCertificadoAux p")
	List<ProdutoCertificadoAux> findAllDistinct();
}
