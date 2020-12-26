package com.edfer.model.logistica;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.edfer.model.enuns.Estado;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Table(name = "ESTADO_PNEU")
@Entity
@Data
public class EstadoPneu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEstadoPneu;

	@JsonInclude(Include.NON_NULL)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idPneu")
	@JsonIgnore
//	@JsonIgnoreProperties(value = { "estadosPneu", "historico", "posicao", "numeroEixo", "veiculo", "ativo", "numSerie",
//			"codControleEdfer", "numNotaOuNumOrdem", "modelo", "fabricantePneu", "dataCompra" }, allowGetters = true)
	private Pneu pneu;

	@JsonInclude(Include.NON_NULL)
	private Long kmFinal;

	@JsonInclude(Include.NON_NULL)
	@NotNull(message="Campo data deve ser preenchido")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
	private LocalDate dataReforma;

	@Enumerated(EnumType.STRING)
	@NotNull(message="Campo estado deve ser preenchido")
	private Estado estado;

	public void addPneu(Pneu pneu) {
		if (pneu != null) {
			this.pneu = pneu;
		}
	}
}
