package com.edfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edfer.model.OrcamentoItemFornecedor;

@Repository
public interface OrcamentoItemFornecedorRepository extends JpaRepository<OrcamentoItemFornecedor, Long>{

}
