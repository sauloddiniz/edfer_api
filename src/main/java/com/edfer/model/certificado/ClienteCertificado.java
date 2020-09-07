package com.edfer.model.certificado;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.edfer.service.auditable.Auditable;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "idClienteCertificado", callSuper = false)
@Setter
@Entity
@Table(name = "CERTIFICADO_CLIENTE")
public class ClienteCertificado extends Auditable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idClienteCertificado;

	@NotBlank(message="Nome cliente é obrigatório")
	private String nome;

	@NotBlank(message="Nº Pedido é obrigatório")
	private String numPedido;

	@ManyToOne
	@JoinColumn(name = "idProdutoCertificadoAux")
	private ProdutoCertificadoAux produtoCertificadoAux;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
	private LocalDate data;

	public ClienteCertificado(String nome) {
		this.nome = nome;
	}
	
}
