package com.edfer.model.logistica;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.edfer.model.enuns.EnunCategoriaCnh;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@DiscriminatorValue("1")
public class Ajudante extends LogisticaFuncionario{
	private static final long serialVersionUID = 1L;

	@NotNull
	@Enumerated(EnumType.STRING)
	private EnunCategoriaCnh enumCategoriaCnh;
}
