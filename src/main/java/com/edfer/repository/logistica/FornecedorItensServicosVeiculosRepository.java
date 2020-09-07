package com.edfer.repository.logistica;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edfer.model.logistica.FornecedorItensServicosVeiculos;

@Repository
public interface FornecedorItensServicosVeiculosRepository extends JpaRepository<FornecedorItensServicosVeiculos, Long>{

}
