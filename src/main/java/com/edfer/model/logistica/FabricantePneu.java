package com.edfer.model.logistica;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.edfer.model.Fabricante;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name="FABRICANTE_PNEU")
public class FabricantePneu extends Fabricante{
	private static final long serialVersionUID = 1L;

}
