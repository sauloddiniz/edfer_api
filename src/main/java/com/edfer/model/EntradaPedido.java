package com.edfer.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.edfer.service.auditable.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "ENTRADA_PEDIDO")
@Data
public class EntradaPedido extends Auditable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEntradaPedido;

	@OneToOne
	@JoinColumn(name = "idPedido")
	@JsonIgnoreProperties(value = { "entradaPedido" })
	private Pedido pedido;

	@Column(unique = true, name = "numeroPedido")
	@NotBlank(message = "O Nº do pedido não pode estar em branco")
	@NotEmpty(message = "O Nº do pedido não pode ser vazio")
	@Size(min = 6, max = 10, message = "O Nº do pedido deve conter de 6 á 10 numeros")
	private String numeroPedido;

	@NotBlank(message = "O cliente não pode estar em branco")
	@NotEmpty(message = "O cliente não pode ser vazio")
	@Size(min = 3, message = "Nome do cliente requer o minimo de 3 caracteres")
	private String cliente;
}
