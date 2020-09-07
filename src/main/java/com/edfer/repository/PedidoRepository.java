package com.edfer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.Pedido;
import com.edfer.model.enuns.EnumLocalizacaoPedido;
import com.edfer.model.enuns.EnumStatusPedido;

@Repository
@Transactional
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	@Transactional(readOnly = true)
	@Query(value="SELECT p.rota FROM Pedido p WHERE p.rota LIKE :rota% GROUP BY p.rota")
	List<String> findAllRotas(String rota);

	@Transactional(readOnly = true)
	@Query(value="SELECT p.localizacao FROM Pedido p WHERE p.localizacao LIKE :localizacao% GROUP BY p.localizacao")
	List<String> findAllLocalizacao(String localizacao);

	long countByLocalizacaoPedidoAndStatus(EnumLocalizacaoPedido localizacao, EnumStatusPedido status);
}
