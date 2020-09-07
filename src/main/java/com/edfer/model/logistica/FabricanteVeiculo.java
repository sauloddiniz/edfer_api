package com.edfer.model.logistica;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.edfer.model.Fabricante;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="FABRICANTE_VEICULO")
public class FabricanteVeiculo extends Fabricante{
	private static final long serialVersionUID = 1L;
}
