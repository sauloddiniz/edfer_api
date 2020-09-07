package com.edfer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@EqualsAndHashCode(of = "idOrcamentoItem")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ORCAMENTO_ITEM")
public class OrcamentoItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idOrcamentoItem;

	private String descricao;

	private int quantidade;

	@ManyToOne
	@JoinColumn(name = "idOrcamento")
	@JsonIgnoreProperties(value = { "listaItemOrc" })
	private Orcamento orcamento;

	@OneToMany(mappedBy = "orcamentoItem", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = { "orcamentoItem" })
	private List<OrcamentoItemFornecedor> fornecedores;

	public void addFornecedor(OrcamentoItemFornecedor fornecedor) {
		if (this.fornecedores != null) {
			this.fornecedores = new ArrayList<>();
		}
		fornecedor.setOrcamentoItem(this);
		this.fornecedores.add(fornecedor);
	}
}
