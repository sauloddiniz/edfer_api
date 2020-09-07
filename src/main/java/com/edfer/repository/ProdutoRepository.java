package com.edfer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.edfer.model.Produto;
import com.edfer.repository.extend.ProdutoRepositoryExtends;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryExtends{

	Optional<Produto> findByDescricao(String descricao);

	Optional<Produto> findByCodigo(String codigo);

	List<Produto> findByCodigoStartingWith(String codigo);

	@Query("SELECT p FROM Produto p WHERE p.descricao LIKE %:descricao%")
	List<Produto> findByDescricaoContaining(String descricao);
}
