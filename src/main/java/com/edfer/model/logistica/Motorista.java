package com.edfer.model.logistica;

import java.time.LocalDate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@DiscriminatorValue("0")
public class Motorista extends LogisticaFuncionario{
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Numero CNH é obrigatório")
	private String numberCnh;

	@NotNull
	@Enumerated(EnumType.STRING)
	private EnunCategoriaCnh enumCategoriaCnh;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
	@JsonInclude(Include.NON_NULL)
	private LocalDate dataVencimentoCnh;
}
