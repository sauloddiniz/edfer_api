package com.edfer.repository.almoxerifado;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edfer.model.almoxerifado.Miudeza;

@Repository
public interface MiudezaRepository extends JpaRepository<Miudeza, Long>, MiudezaRepositoryExtends {

	List<Miudeza> findByCodigoStartingWith(String codigo);

	Optional<Miudeza> findByCodigo(String codigo);
}
