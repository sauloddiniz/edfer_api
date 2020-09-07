package com.edfer.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.edfer.model.enuns.EnumLocalizacaoPedido;
import com.edfer.model.enuns.EnumSituacaoFinalPedido;
import com.edfer.model.enuns.EnumStatusPedido;
import com.edfer.service.auditable.Auditable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Entity
@Table(name = "PEDIDO")
@Data
public class Pedido extends Auditable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPedido;

	@OneToOne(mappedBy = "pedido")
	@JsonIgnoreProperties(value = { "pedido" })
	private EntradaPedido entradaPedido;

	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "Peso não pode ser vazio")
	private Double pesoPedido;

	@JsonInclude(Include.NON_NULL)
	@NotBlank(message = "Rota deve conter um valor")
	@NotEmpty(message = "Rota não pode ser vazio")
	private String rota;

	@JsonInclude(Include.NON_NULL)
	@NotBlank(message = "Localição deve conter um valor")
	@NotEmpty(message = "Localização não pode ser vazio")
	private String localizacao;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "Data Prevista não pode ser vazio")
	private LocalDate dataPrevista;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
	@JsonInclude(Include.NON_NULL)
	private LocalDate dataEntrega;

	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "Localização do pedido não pode ser vazio")
	@Enumerated(EnumType.STRING)
	private EnumLocalizacaoPedido localizacaoPedido;

	@JsonInclude(Include.NON_NULL)
	@Enumerated(EnumType.STRING)
	private EnumSituacaoFinalPedido situacaoFinal;

	@JsonInclude(Include.NON_NULL)
	@Enumerated(EnumType.STRING)
	@NotNull(message = "Status não pode ser vazio")
	private EnumStatusPedido status;

	@JsonInclude(Include.NON_EMPTY)
	private String observacao;

	@JsonInclude(Include.NON_NULL)
	private boolean checkOut;

	@OneToOne(mappedBy = "pedido")
	@JsonIgnoreProperties(value = { "pedido" })
	private PedidoSeparacao pedidoSeparacao;
}
