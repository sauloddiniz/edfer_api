package com.edfer.model.logistica;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.edfer.model.enuns.Posicao;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "idHistorico")
@ToString
@Entity
@Table(name = "HISTORICO_PNEU")
public class HistoricoPneu implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonInclude(Include.NON_NULL)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idHistorico;

	@JsonInclude(Include.NON_NULL)
	@ManyToOne
	@JoinColumn(name = "idPneu")
	@JsonIgnoreProperties(value = { "historico", "tipo", "marca", "numNotaOuNumOrdem", "dataCompra", "numSerie",
			"hodometro", "kmFinal", "kmInical", "enumAtivo" })
	private Pneu pneu;

	@JsonInclude(Include.NON_NULL)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
	private LocalDate dataHistorico;

	@JsonInclude(Include.NON_NULL)
	private String placa;

	@JsonInclude(Include.NON_NULL)
	private int numeroEixo;

	@JsonInclude(Include.NON_NULL)
	@Enumerated(EnumType.STRING)
	private Posicao posicao;

	@JsonInclude(Include.NON_NULL)
	private String servico;

	@JsonInclude(Include.NON_NULL)
	private Long kmServico;

	@JsonInclude(Include.NON_NULL)
	private BigDecimal valorServico;
}
