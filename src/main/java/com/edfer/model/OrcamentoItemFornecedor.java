package com.edfer.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name = "ORCAMENTO_ITEM_FORNECEDOR")
public class OrcamentoItemFornecedor extends Fornecedor {
	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "fornecedor")
	@JsonIgnoreProperties(value = { "fornecedor" })
	private FormaPagamento formaPagamento;

	@ManyToOne
	@JoinColumn(name = "idItemOrcamento")
	@JsonIgnoreProperties(value = { "fornecedores" })
	private OrcamentoItem orcamentoItem;

	private boolean fornecedorSelect;

	public void addFormaPagamento(FormaPagamento formaPagamento) {
		if (this.formaPagamento != null) {
			formaPagamento.setFornecedor(this);
		}
	}
}
