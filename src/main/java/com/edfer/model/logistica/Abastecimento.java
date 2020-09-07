package com.edfer.model.logistica;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.edfer.model.enuns.TipoCombustivel;
import com.edfer.service.auditable.Auditable;
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

@Entity
@Table(name = "ABASTECIMENTO")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode(of = "idAbastecimento", callSuper = false)
public class Abastecimento extends Auditable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAbastecimento;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "Brazil/East")
	private LocalDateTime dataAbastecimento;

	@ManyToOne
	@JoinColumn(name = "idVeiculo")
	@JsonInclude(Include.NON_NULL)
	@JsonIgnoreProperties({ "abastecimentos", "manutencoes" })
	private Veiculo veiculo;

	@ManyToOne
	@JoinColumn(name = "idPosto")
	private Posto posto;

	@Enumerated(EnumType.STRING)
	private TipoCombustivel tipoCombustivel;

	@JsonInclude(Include.NON_NULL)
	@NotNull(message="Hodômetro deve ser preenchido")
	private Long hodometro;

	@JsonInclude(Include.NON_NULL)
	private double quantidadeLitro;

	@JsonInclude(Include.NON_NULL)
	private double consumoMedio;

	@JsonInclude(Include.NON_NULL)
	private BigDecimal valorLitro;

	@JsonInclude(Include.NON_NULL)
	private BigDecimal valorTotal;

	@PrePersist
	@PreUpdate
	private void setValorLitro() {
		RoundingMode roundingMode = RoundingMode.UP;
		this.valorLitro = (this.valorTotal.divide (new BigDecimal(this.quantidadeLitro), 2, roundingMode)); 
	}

	public void updateConsumoMedio(double kmFinal) {
		this.consumoMedio = (this.hodometro - kmFinal) / quantidadeLitro;
	}
}
