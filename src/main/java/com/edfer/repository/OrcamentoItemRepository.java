package com.edfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edfer.model.OrcamentoItem;

@Repository
public interface OrcamentoItemRepository extends JpaRepository<OrcamentoItem, Long>{

}
