package com.edfer.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="idProdutoChegada")
@ToString
@Getter
@Setter
@Entity
@Table(name = "PRODUTO_CHEGADA")
public class ProdutoChegada implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idProdutoChegada;

	@ManyToOne
	@JoinColumn(name="idProduto")
	@JsonIgnoreProperties(value={"produtoChegada"})
	private Produto produto;

	private double pesoProdutoChegada;

	@ManyToOne
	@JoinColumn(name="idPrevisaoChegadaProduto")
	@JsonIgnoreProperties(value="produtosChegada")
	private PrevisaoChegadaProduto previsaoChegadaProduto;

}
