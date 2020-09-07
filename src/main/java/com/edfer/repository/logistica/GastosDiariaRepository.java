package com.edfer.repository.logistica;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edfer.model.logistica.GastosDiaria;

@Repository
public interface GastosDiariaRepository extends JpaRepository<GastosDiaria, Long>{

}
