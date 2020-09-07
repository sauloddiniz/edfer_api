package com.edfer.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.edfer.model.enuns.TipoPagamento;
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
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "FORMA_PAGAMENTO")
public class FormaPagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFormaPagamento;

	private BigDecimal valorAv;

	private int quantParcela;

	private BigDecimal valorParcela;

	private TipoPagamento tipoPagamentoSelect;

	@OneToOne
	@JoinColumn(name = "idOrcamentoFornecedor")
	@JsonIgnoreProperties(value = { "formaPagamento" })
	private OrcamentoItemFornecedor fornecedor;
}
