package com.edfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.EntradaPedido;
import com.edfer.repository.extend.EntradaPedidoRepositoryExtends;

@Repository
@Transactional
public interface EntradaPedidoRepository extends JpaRepository<EntradaPedido, Long>, EntradaPedidoRepositoryExtends {

	long countByPedidoIsNull();
}
