package com.edfer.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name="FORNECEDOR_PRODUTO")
public class FornecedorProduto extends Fornecedor {
	private static final long serialVersionUID = 1L;
}
