package com.edfer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edfer.model.FornecedorProduto;

@Repository
public interface FornecedorProdutoRepository extends JpaRepository<FornecedorProduto, Long>{

	Optional<FornecedorProduto> findByNome(String nome);
	
	List<FornecedorProduto> findByNomeStartingWith(String nome);
}
