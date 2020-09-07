package com.edfer.repository.almoxerifado;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.edfer.model.almoxerifado.EntradaSaidaMiudeza;

@Repository
public interface EntradaSaidaRepository extends JpaRepository<EntradaSaidaMiudeza, Long> {

	//@Query(value="SELECT * FROM edfer_logistica.entrada_saida ORDER BY id_entrada_saida DESC LIMIT 10", nativeQuery=true)
	@Query(value="SELECT * FROM bd_edfer.entrada_saida ORDER BY id_entrada_saida DESC", nativeQuery=true)
	List<EntradaSaidaMiudeza> findAllOrderByIdEntradaSaida();
}
