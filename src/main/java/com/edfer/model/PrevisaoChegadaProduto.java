package com.edfer.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PREVISAO_CHEGADA_PRODUTO")
public class PrevisaoChegadaProduto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idPrevisaoChegadaProduto;

	private LocalDate dataPrevisaoEntrega;

	@OneToMany(mappedBy="previsaoChegadaProduto", cascade=CascadeType.ALL)
	@JsonIgnoreProperties(value={"previsaoChegadaProduto"}, allowSetters=true)
	private List<ProdutoChegada> produtosChegada;

	@OneToOne
	@JoinColumn(name="idFornecedor")
	private FornecedorProduto fornecedorProduto;

	private String observacao;

	public void addProdutoChegada(ProdutoChegada produto) {
		if(produtosChegada != null) {
			this.produtosChegada = new ArrayList<>();
		}
		produto.setPrevisaoChegadaProduto(this);
		this.produtosChegada.add(produto);
	}
}
