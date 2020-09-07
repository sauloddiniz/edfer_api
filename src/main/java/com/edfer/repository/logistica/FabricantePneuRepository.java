package com.edfer.repository.logistica;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edfer.model.logistica.FabricantePneu;

@Repository
public interface FabricantePneuRepository extends JpaRepository<FabricantePneu, Long>{

}
