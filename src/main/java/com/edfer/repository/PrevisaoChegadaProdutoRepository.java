package com.edfer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edfer.model.PrevisaoChegadaProduto;

@Repository
public interface PrevisaoChegadaProdutoRepository extends JpaRepository<PrevisaoChegadaProduto, Long>{

	List<PrevisaoChegadaProduto> findAllByOrderByDataPrevisaoEntregaAsc();
}
