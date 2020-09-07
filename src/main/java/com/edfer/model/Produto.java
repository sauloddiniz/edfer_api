package com.edfer.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "idProduto")
@ToString
@Getter
@Setter
@Entity
@Table(name = "PRODUTO")
public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProduto;

	@JsonInclude(Include.NON_NULL)
	private String codigo;

	@JsonInclude(Include.NON_NULL)
	private String descricao;

	@OneToMany(mappedBy = "produto")
	@JsonInclude(Include.NON_NULL)
	@JsonIgnoreProperties(value = { "produto", "previsaoChegadaProduto" })
	private List<ProdutoChegada> produtoChegada;

	public Produto(Long idProduto, String codigo, String descricao) {
		this.idProduto = idProduto;
		this.codigo = codigo;
		this.descricao = descricao;
	}
}
