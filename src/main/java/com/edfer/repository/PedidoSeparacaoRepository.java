package com.edfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edfer.model.PedidoSeparacao;

@Repository
public interface PedidoSeparacaoRepository extends JpaRepository<PedidoSeparacao, Long>{

}
