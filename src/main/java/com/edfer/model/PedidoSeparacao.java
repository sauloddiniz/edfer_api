package com.edfer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.edfer.service.auditable.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "PEDIDO_SEPARACAO")
public class PedidoSeparacao extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPedidoSeparacao;

	private boolean separado;

	@OneToOne
	@JoinColumn(name = "idPedido")
	@JsonIgnoreProperties(value = { "pedidoSeparacao" })
	private Pedido pedido;
}
