package com.edfer.model.logistica;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.edfer.model.enuns.EnumTipoGasto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "GASTOS_DIARIA")
public class GastosDiaria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idGastosDiaria;

	@Enumerated(EnumType.STRING)
	private EnumTipoGasto tipoGasto;

	private BigDecimal valor;

	private String observacao;

	@ManyToOne
	@JoinColumn(name = "IdParteDiaira")
	@JsonIgnoreProperties(value = "gastos")
	private ParteDiaria parteDiaria;
}
